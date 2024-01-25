package com.pamela.flashcards.features.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.component.AlertText
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.HourPickerWithLabel
import com.pamela.flashcards.ui.component.IncrementerWithTextLabel
import com.pamela.flashcards.ui.component.StyledButton
import com.pamela.flashcards.ui.component.StyledTopBar
import com.pamela.flashcards.ui.component.SwitchWithTextLabel
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.colorTextNeutral

@Composable
fun NotificationsSettingsScreen(viewModel: NotificationsSettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val getNotificationsPermissions =
        {
            if (Build.VERSION.SDK_INT > 32) ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) else PackageManager.PERMISSION_GRANTED
        }
    var hasPermission by remember {
        mutableStateOf(getNotificationsPermissions() == PackageManager.PERMISSION_GRANTED)
    }
    val notificationsPermissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted -> hasPermission = isGranted }

    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = {
            StyledTopBar(
                titleText = stringResource(id = R.string.notifications_settings_header),
                onClickNavigation = viewModel::openNavDrawer
            )
        },
        bottomBar = {
            if (hasPermission) {
                BottomBarButtonFullWidth(
                    onClick = viewModel::saveSettings,
                    text = stringResource(id = R.string.save),
                    icon = Icons.Rounded.Check
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = if (hasPermission) 32.dp else 16.dp,
                alignment = if (hasPermission) Alignment.Top else Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (hasPermission) {
                SwitchWithTextLabel(
                    label = stringResource(id = R.string.send_notifications),
                    value = uiState.notificationsPreferences.shouldSendFlashCards,
                    onClick = { viewModel.updateNotificationsPreferences(shouldSendFlashCards = it) },
                )
                IncrementerWithTextLabel(
                    label = stringResource(id = R.string.max_notifications),
                    value = uiState.notificationsPreferences.maxFlashCardsSentPerDay,
                    onChange = { viewModel.updateNotificationsPreferences(maxFlashCardsSentPerDay = it) },
                )
                HourPickerWithLabel(
                    label = stringResource(id = R.string.set_start_time),
                    value = uiState.notificationsPreferences.flashCardsStartHour,
                    onChange = { viewModel.updateNotificationsPreferences(flashCardsStartHour = it) },
                )
                HourPickerWithLabel(
                    label = stringResource(id = R.string.set_end_time),
                    value = uiState.notificationsPreferences.flashCardsEndHour,
                    onChange = { viewModel.updateNotificationsPreferences(flashCardsEndHour = it) },
                )
                Spacer(modifier = Modifier.height(8.dp))
                StyledButton(
                    onClick = viewModel::testNotification,
                    text = "Test notifications",
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(0.4F),
                    tint = MaterialTheme.colorTextNeutral()
                )
                AlertText(
                    text = stringResource(id = R.string.why_notifications),
                    color = MaterialTheme.colorTextNeutral()
                )
                StyledButton(
                    onClick = { notificationsPermissionsLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                    text = stringResource(id = R.string.allow_notifications)
                )
            }
        }
    }
}