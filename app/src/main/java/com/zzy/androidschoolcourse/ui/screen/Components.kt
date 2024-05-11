package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularImageButton(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    imageId:Int = ResourceConstants.ADD_IMAGE_ID,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier.size(size),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            DrawCircleWithImage(size = size, imagePainter = painterResource(id = imageId))
        }
    }
}

@Composable
fun DrawCircleWithImage(
    size: Dp,
    imagePainter: Painter,
    color: Color = Color.White
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color, radius = size.toPx() / 2)
        }
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
