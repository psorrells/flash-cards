package com.pamela.flashcards.features.practice

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EmptyDeckDisplay(onClickAddCard: () -> Unit) {
    Column {
        Text("OOPs, no cards. :( Why don't you add one?")
        Button(onClick = onClickAddCard) { Text(text = "Add Card") }
    }
}