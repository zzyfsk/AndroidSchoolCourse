package com.zzy.component.box

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AlphaBox(
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    color: Color = Color.White,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier
        .background(color = color.copy(alpha = alpha))
        .clickable { }) {
        content()
    }
}