package com.pamela.flashcards.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun getFloatAnimationByBoolean(
    isTrue: Boolean,
    trueValue: Float,
    falseValue: Float,
    startValue: Float? = null,
    animationListener: ((Float) -> Unit)? = null,
    animationSpec: AnimationSpec<Float> = tween(1000, easing = EaseOut)
): Float {
    var targetValue by remember { mutableFloatStateOf(startValue ?: falseValue) }
    val animatedValue by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = animationSpec,
        label = "floatAnimationByBoolean",
        finishedListener = animationListener
    )
    LaunchedEffect(key1 = isTrue, block = {
        targetValue = if (isTrue) trueValue else falseValue
    })
    return animatedValue
}

// doesn't work for some reason
@Composable
fun animateFloatOnComposition(
    startValue: Float,
    endValue: Float,
    animationListener: ((Float) -> Unit)? = null,
    animationSpec: AnimationSpec<Float> = tween(1000, easing = EaseOut)
): Float {
    var targetValue by remember { mutableFloatStateOf(startValue) }
    val animatedValue by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = animationSpec,
        label = "widthAnimation",
        finishedListener = animationListener
    )
    LaunchedEffect(key1 = Unit) { targetValue = endValue }
    return animatedValue
}