package com.zzy.androidschoolcourse.ui.screen



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import cafe.adriel.voyager.core.screen.Screen

import com.zzy.androidschoolcourse.ui.screen.DrawCircleWithImage
import com.zzy.androidschoolcourse.ui.screen.CircularImageButton
class ScreenZlh : Screen {

    @Composable
    override fun Content() {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                Column {
                    CircularImageButton(imagePainter = painterResource(id = ResourceConstants.ADD_IMAGE_ID))
                    CircularImageButton(imagePainter = painterResource(id = ResourceConstants.DEL_IMAGE_ID))

                }
                Column {
                    CircularImageButton(imagePainter = painterResource(id = ResourceConstants.MINUS_IMAGE_ID))
                    CircularImageButton(imagePainter = painterResource(id = ResourceConstants.DIVIDE_IMAGE_ID))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
            ) {
                CircularImageButton(imagePainter = painterResource(id = ResourceConstants.MINUS_IMAGE_ID))
            }
        }
    }
}