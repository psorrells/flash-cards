package com.pamela.flashcards.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.theme.colorTextNeutral
import kotlinx.coroutines.launch

@Composable
fun DefaultNavigationDrawer(
    viewModel: NavigationViewModel = hiltViewModel(),
    drawerState: DrawerState,
    extraNavItems: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5F)
                    .fillMaxHeight()
                    .offset(x = if (drawerState.isClosed) (-100).dp else 0.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = stringResource(id = R.string.menu),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { LabelText(text = stringResource(id = R.string.overview_header)) },
                    selected = false,
                    onClick = { coroutineScope.launch { viewModel.navigateToOverview(); drawerState.close() } },
                    shape = RectangleShape
                )
                NavigationDrawerItem(
                    label = { LabelText(text = stringResource(id = R.string.add_deck_header)) },
                    selected = false,
                    onClick = { coroutineScope.launch { viewModel.navigateToAddNewDeck(); drawerState.close() } },
                    shape = RectangleShape
                )
                NavigationDrawerItem(
                    label = { LabelText(text = stringResource(id = R.string.add_card_header)) },
                    selected = false,
                    onClick = { coroutineScope.launch { viewModel.navigateToAddNewCard(); drawerState.close() } },
                    shape = RectangleShape
                )
                NavigationDrawerItem(
                    label = { LabelText(text = stringResource(id = R.string.notifications_settings_header)) },
                    selected = false,
                    onClick = { coroutineScope.launch { viewModel.navigateToNotificationsSettings(); drawerState.close() } },
                    shape = RectangleShape
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
                extraNavItems()
            }
        }) {
        content()
    }
}

@Composable
private fun LabelText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorTextNeutral()
    )
}