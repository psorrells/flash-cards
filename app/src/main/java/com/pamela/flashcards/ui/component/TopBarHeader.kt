package com.pamela.flashcards.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTopBar(titleText: String, onClickNavigation: () -> Unit, actions: @Composable () -> Unit = {}) {
    Column {
        TopAppBar(
            title = {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = titleText,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navigationIcon = {
                IconButton(onClick = onClickNavigation) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = stringResource(id = R.string.menu),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            actions = { actions() }
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}