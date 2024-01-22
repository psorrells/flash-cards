package com.pamela.flashcards.features.addset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pamela.flashcards.R
import com.pamela.flashcards.ui.component.BottomBarButtonFullWidth
import com.pamela.flashcards.ui.component.TextFieldWithLabel
import com.pamela.flashcards.ui.component.TopBarHeader
import com.pamela.flashcards.ui.scaffoldDefaults

@Composable
fun AddSetScreen(viewModel: AddSetViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.scaffoldDefaults(),
        topBar = { TopBarHeader(titleText = viewModel.getPageTitle()) },
        bottomBar = {
            BottomBarButtonFullWidth(
                onClick = viewModel::saveSet,
                text = stringResource(id = R.string.save),
                icon = Icons.Rounded.Check
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            TextFieldWithLabel(
                label = stringResource(id = R.string.set_name_label),
                value = uiState.flashCardSet.name,
                onChangeValue = viewModel::updateName
            )
        }
    }
}