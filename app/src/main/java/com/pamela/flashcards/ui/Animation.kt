package com.pamela.flashcards.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp

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


@Composable
fun animateFloatOnChange(
    startValue: Float,
    animationListener: ((Float) -> Unit)? = null,
    animationSpec: AnimationSpec<Float> = tween(1000, easing = EaseOut)
): AnimationWithSetter<Float> {
    var targetValue by remember { mutableFloatStateOf(startValue) }
    val animatedValue by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = animationSpec,
        label = "floatAnimationOnChange",
        finishedListener = animationListener
    )
    return AnimationWithSetter(animatedValue) { targetValue = it }
}

@Composable
fun animateDpOnChange(
    startValue: Dp,
    animationListener: ((Dp) -> Unit)? = null,
    animationSpec: AnimationSpec<Dp> = tween(1000, easing = EaseOut)
): AnimationWithSetter<Dp> {
    var targetValue by remember { mutableStateOf(startValue) }
    val animatedValue by animateDpAsState(
        targetValue = targetValue,
        animationSpec = animationSpec,
        label = "dpAnimationOnChange",
        finishedListener = animationListener
    )
    return AnimationWithSetter(animatedValue) { targetValue = it }
}

data class AnimationWithSetter<T>(
    val value: T,
    val setValue: (T) -> Unit
)