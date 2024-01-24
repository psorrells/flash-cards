package com.pamela.flashcards.database.preferences

import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.pamela.flashcards.model.NotificationsPreferencesDomain
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

interface PreferencesRepository {
    suspend fun setShouldSendFlashCardNotifications(boolean: Boolean)

    suspend fun setMaxNotificationCardsPerDay(int: Int)

    suspend fun setNotificationsStartHour(int: Int)

    suspend fun setNotificationsEndHour(int: Int)

    suspend fun getNotificationsPreferences(): NotificationsPreferencesDomain
}

class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>
) : PreferencesRepository {
    override suspend fun setShouldSendFlashCardNotifications(boolean: Boolean) {
        preferencesDataStore.edit { preferences ->
            preferences[KEY_SHOULD_SEND_NOTIFICATIONS] = boolean
        }
    }

    override suspend fun setMaxNotificationCardsPerDay(int: Int) {
        preferencesDataStore.edit { preferences ->
            preferences[KEY_MAX_NOTIFICATION_CARDS_PER_DAY] = int
        }
    }

    override suspend fun setNotificationsStartHour(int: Int) {
        preferencesDataStore.edit { preferences ->
            preferences[KEY_NOTIFICATIONS_START] = int
        }
    }

    override suspend fun setNotificationsEndHour(int: Int) {
        preferencesDataStore.edit { preferences ->
            preferences[KEY_NOTIFICATIONS_END] = int
        }
    }

    override suspend fun getNotificationsPreferences(): NotificationsPreferencesDomain {
        val preferences = preferencesDataStore.data.first()

        return NotificationsPreferencesDomain(
            shouldSendFlashCards = preferences[KEY_SHOULD_SEND_NOTIFICATIONS] ?: false,
            flashCardsStartHour = preferences[KEY_NOTIFICATIONS_START] ?: 0,
            flashCardsEndHour = preferences[KEY_NOTIFICATIONS_END] ?: 23,
            maxFlashCardsSentPerDay = preferences[KEY_MAX_NOTIFICATION_CARDS_PER_DAY] ?: 0
        )
    }

    companion object {
        private val KEY_SHOULD_SEND_NOTIFICATIONS = booleanPreferencesKey("shouldSendNotifications")
        private val KEY_MAX_NOTIFICATION_CARDS_PER_DAY =
            intPreferencesKey("maxNotificationCardsPerDay")
        private val KEY_NOTIFICATIONS_START = intPreferencesKey("notificationsStartHour")
        private val KEY_NOTIFICATIONS_END = intPreferencesKey("notificationsEndHour")
    }
}