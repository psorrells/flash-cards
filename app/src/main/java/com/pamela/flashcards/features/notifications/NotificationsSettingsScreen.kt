package com.pamela.flashcards.features.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Notifications
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
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.component.AlertText
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.StyledButton
import com.pamela.flashcards.ui.component.StyledTopBar
import com.pamela.flashcards.ui.scaffoldDefaults
import com.pamela.flashcards.ui.theme.colorTextNeutral

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationsSettingsScreen(viewModel: NotificationsSettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val getNotificationsPermissions =
        { ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) }
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
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (hasPermission) {
                Text(text = "You have permissions!")
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