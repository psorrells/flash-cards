package com.pamela.flashcards.features.practice

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.pamela.flashcards.model.FlashCardDomain
import kotlin.math.abs
import kotlin.math.sin

@Composable
fun FlashCard(
    isFlipped: Boolean,
    setIsFlipped: (Boolean) -> Unit,
    card: FlashCardDomain
) {
    var rotationValue by remember { mutableFloatStateOf(0.0F) }
    val rotation by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "flashCardRotate"
    )
    val size by animateFloatAsState(
        targetValue = abs(sin(((rotation) * Math.PI / 180.0) + Math.PI / 2.0).toFloat()),
        animationSpec = tween(50),
        label = "flashCardSize"
    )

    LaunchedEffect(key1 = isFlipped, block = {
        rotationValue = if (isFlipped) {
            180.0F
        } else {
            0.0F
        }
    })

    if (rotation > 90.0F) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxHeight(size / 2.0F + 0.5F)
                .fillMaxWidth(size)
                .clickable { setIsFlipped(false) }
                .rotate(-rotation + 180.0F)
        ) {
            if (rotation >= 170.0F) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .border(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(12.dp),
                            width = 2.dp
                        )
                ) {
                    Text(text = card.back)
                }
            }
        }
    }
    if (rotation <= 90.0F) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxHeight(size / 2.0F + 0.5F)
                .fillMaxWidth(size)
                .clickable { setIsFlipped(true) }
                .rotate(-rotation),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            if (rotation <= 10.0F) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize(size)
                        .padding(16.dp)
                        .border(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(12.dp),
                            width = 2.dp
                        )
                ) {
                    Text(text = card.front)
                }
            }
        }
    }
}