package com.zzy.component.toast

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Toast(
    message: String?,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    Box(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
        val hostState = remember { SnackbarHostState() }
        SnackbarHost(hostState = hostState, modifier = Modifier.align(BiasAlignment(0f, 0.9f)))
        LaunchedEffect(key1 = message) {
            if (message != null) {
                scope.launch {
                    hostState.showSnackbar(
                        message = message,
                        actionLabel = "",
                        withDismissAction = true,
                        duration = duration
                    )
                }
            }

        }
    }
}

@Composable
fun ToastWait() {
    Box(modifier = Modifier.fillMaxSize()) {
        val hostState = SnackbarHostState()

        SnackbarHost(hostState = hostState, modifier = Modifier.align(Alignment.Center)) {
            Card(
                Modifier
                    .size(96.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                var isStop by remember {
                    mutableStateOf(false)
                }
                val progress = animateFloatAsState(
                    targetValue = if (isStop) 0.75f else 0.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "progress"
                )
                val rotate = animateFloatAsState(
                    targetValue = if (isStop) 360f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "rotate"
                )
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        progress = { progress.value },
                        modifier = Modifier
                            .size(50.dp)
                            .align(
                                Alignment.Center
                            )
                            .rotate(rotate.value),
                        color = Color.Yellow,
                        strokeWidth = 4.dp,
                        trackColor = Color.Transparent,
                        strokeCap = StrokeCap.Round,
                    )
                }
                LaunchedEffect(key1 = "progress", block = {
                    isStop = true
                })
            }
        }
        LaunchedEffect(key1 = "Toast", block = {
            hostState.showSnackbar(message = "成功提示", duration = SnackbarDuration.Indefinite)
        })
        LaunchedEffect(key1 = "dismiss", block = {
            delay(10000L)
            hostState.currentSnackbarData?.dismiss()
        })
    }
}