package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.R

class ScreenZlh:Screen {

    @Composable
    override fun Content() {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                Column {
                    SquareButton(num = "6")
                    SquareButton(num = "7")

                }
                Column {
                    SquareButton(num = "8")
                    SquareButton(num = "9")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
            ) {
                SquareButton()
            }
        }
    }

    @Composable
    fun SquareButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {},
        imageId:Int = Companion.ADD_IMAGE_ID,
        buttonSize: Dp = 64.dp,
        buttonColor: Color = Color.Blue,
        textColor: Color = Color.White
    ) {
        Button(
            onClick = onClick,
            modifier = modifier
                .size(buttonSize)
                .background(color = buttonColor, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
            contentPadding = PaddingValues(8.dp)
        ) {
            androidx.compose.material.Text(
                text = buttonText,
                color = textColor,
                fontSize = 18.sp
            )
        }
    }

    companion object {
        val ADD_IMAGE_ID = R.drawable.add
        val DEL_IMAGE_ID = R.drawable.del
        val MINUS_IMAGE_ID = R.drawable.mul
        val DIVIDE_IMAGE_ID = R.drawable.div
    }
}

