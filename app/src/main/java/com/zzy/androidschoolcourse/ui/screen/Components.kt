package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zzy.component.background.CanvasWave

@Composable
fun CircularImageButton(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    imageId: Int = ResourceConstants.ADD_IMAGE_ID,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier.size(size),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            DrawCircleWithImage(imagePainter = painterResource(id = imageId))
        }
    }
}

@Composable
fun DrawCircleWithImage(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    imagePainter: Painter
) {
    Box(modifier = modifier) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = modifier.size(size = size),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun BoxWave(
    modifier: Modifier = Modifier,
    height: Dp = 100.dp,
    color:Color = MaterialTheme.colorScheme. primary,
    content: @Composable () -> Unit = {}
) {
    val widthX = LocalConfiguration.current.screenWidthDp
    val width = (widthX + 1).dp
    Box(modifier = modifier) {
        CanvasWave(modifier = Modifier.fillMaxWidth(), height = height, width = width, color = color)
        Column(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}