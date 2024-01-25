package com.pamela.flashcards.features.notifications

import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamela.flashcards.domain.notification.CancelFlashCardAlarmUseCase
import com.pamela.flashcards.domain.notification.CreateFlashCardAlarmUseCase
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase
import com.pamela.flashcards.domain.notification.NotificationsPreferencesOperations
import com.pamela.flashcards.model.NotificationsPreferencesDomain
import com.pamela.flashcards.navigation.NavDrawerDestination
import com.pamela.flashcards.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class NotificationsSettingsViewModel @Inject constructor(
    private val createNextDueCardNotification: CreateNextDueCardFrontNotificationUseCase,
    private val preferencesOperations: NotificationsPreferencesOperations,
    private val createFlashCardAlarm: CreateFlashCardAlarmUseCase,
    private val cancelFlashCardAlarm: CancelFlashCardAlarmUseCase,
    private val notificationManagerCompat: NotificationManagerCompat,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsSettingsUiState())
    val uiState = _uiState.asStateFlow()
    private val logger: Logger = Logger.getLogger("NotificationsSettings")

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.info("error in notifications settings: ${throwable.message}")
        _uiState.update {
            it.copy(errorState = throwable)
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            preferencesOperations.getNotificationsPreferences().getOrThrow().let { preferences ->
                _uiState.update {
                    it.copy(notificationsPreferences = preferences)
                }
            }
        }
    }

    fun openNavDrawer() {
        navigator.navigateTo(NavDrawerDestination.route)
    }

    fun testNotification() {
        viewModelScope.launch {
            createNextDueCardNotification()
        }
    }

    fun updateNotificationsPreferences(
        shouldSendFlashCards: Boolean? = null,
        flashCardsStartHour: Int? = null,
        flashCardsEndHour: Int? = null,
        maxFlashCardsSentPerDay: Int? = null
    ) {
        val shouldSend = shouldSendFlashCards
            ?: uiState.value.notificationsPreferences.shouldSendFlashCards
        val startHour = validateAndReturnStartHour(flashCardsStartHour)
        val endHour = validateAndReturnEndHour(flashCardsEndHour)
        val maxFlashCards = validateAndReturnMaxFlashCards(maxFlashCardsSentPerDay)


        val newPrefs = NotificationsPreferencesDomain(
            shouldSendFlashCards = shouldSend,
            flashCardsStartHour = startHour,
            flashCardsEndHour = endHour,
            maxFlashCardsSentPerDay = maxFlashCards
        )

        _uiState.update {
            it.copy(notificationsPreferences = newPrefs)
        }
    }

    private fun validateAndReturnStartHour(hour: Int?): Int {
        val endHour = uiState.value.notificationsPreferences.flashCardsEndHour
        return if (hour != null && hour < endHour) hour
        else uiState.value.notificationsPreferences.flashCardsStartHour
    }

    private fun validateAndReturnEndHour(hour: Int?): Int {
        val startHour = uiState.value.notificationsPreferences.flashCardsStartHour
        return if (hour != null && hour > startHour) hour
        else uiState.value.notificationsPreferences.flashCardsEndHour
    }

    private fun validateAndReturnMaxFlashCards(count: Int?): Int {
        return count?.coerceAtMost(50)?.coerceAtLeast(0)
            ?: uiState.value.notificationsPreferences.maxFlashCardsSentPerDay
    }

    fun saveSettings() {
        viewModelScope.launch(exceptionHandler) {
            val newPrefs = uiState.value.notificationsPreferences
            preferencesOperations.setNotificationsPreferences(newPrefs).getOrThrow()
            if (newPrefs.shouldSendFlashCards) {
                createFlashCardAlarm()
            } else {
                cancelFlashCardAlarm()
                notificationManagerCompat.cancelAll()
            }
            navigator.popBackStack()
        }
    }
}

data class NotificationsSettingsUiState(
    val notificationsPreferences: NotificationsPreferencesDomain = NotificationsPreferencesDomain(),
    val errorState: Throwable? = null
)


