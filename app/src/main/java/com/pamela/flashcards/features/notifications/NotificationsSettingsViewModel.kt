package com.pamela.flashcards.features.notifications

import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ListenableWorker.Result.Failure
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase
import com.pamela.flashcards.domain.notification.GetNotificationsPreferencesUseCase
import com.pamela.flashcards.domain.notification.NotificationsPreferencesOperations
import com.pamela.flashcards.domain.notification.SetMaxFlashCardNotificationsPerDayUseCase
import com.pamela.flashcards.features.addcard.AddCardUiState
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
import javax.inject.Inject

@HiltViewModel
class NotificationsSettingsViewModel @Inject constructor(
    private val createNextDueCardNotification: CreateNextDueCardFrontNotificationUseCase,
    private val notificationsPreferences: NotificationsPreferencesOperations,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsSettingsUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update {
            it.copy(errorState = throwable)
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            notificationsPreferences.getNotificationsPreferences().getOrThrow().let { preferences ->
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

    fun saveSettings() {
        viewModelScope.launch(exceptionHandler) {
            val newPrefs = uiState.value.notificationsPreferences
            val result = awaitAll(
                async {
                    notificationsPreferences.setShouldShowFlashCardNotifications(
                        newPrefs.shouldSendFlashCards
                    )
                },
                async {
                    notificationsPreferences.setMaxFlashCardNotifications(
                        newPrefs.maxFlashCardsSentPerDay
                    )
                },
                async {
                    notificationsPreferences.setNotificationsStartHour(
                        newPrefs.flashCardsStartHour
                    )
                },
                async {
                    notificationsPreferences.setNotificationsEndHour(
                        newPrefs.flashCardsEndHour
                    )
                }
            )
            result.forEach { it.getOrThrow() }

        }
    }
}

data class NotificationsSettingsUiState(
    val notificationsPreferences: NotificationsPreferencesDomain = NotificationsPreferencesDomain(),
    val errorState: Throwable? = null
)


