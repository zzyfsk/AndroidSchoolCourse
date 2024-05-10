package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.R

class ScreenTest : Screen {
    @Composable
    override fun Content() {
        val clickedButton = remember {
            mutableIntStateOf(0)
        }
        val modifier = Modifier
        Column(modifier = Modifier.fillMaxSize()) {
            Text("This is Test Screen")
            Row {
                ButtonMath(
                    modifier = modifier.weight(1f),
                    imageId = R.drawable.add,
                    buttonId = 1,
                    clicked = clickedButton.intValue == 1,
                    onClick = {
                        clickedButton.intValue = it
                    })
                ButtonMath(
                    modifier = modifier.weight(1f),
                    imageId = R.drawable.del,
                    buttonId = 2,
                    clicked = clickedButton.intValue == 2,
                    onClick = {
                        clickedButton.intValue = it
                    })
                ButtonMath(
                    modifier = modifier.weight(1f),
                    imageId = R.drawable.mul,
                    buttonId = 3,
                    clicked = clickedButton.intValue == 3,
                    onClick = {
                        clickedButton.intValue = it
                    })
                ButtonMath(
                    modifier = modifier.weight(1f),
                    imageId = R.drawable.div,
                    buttonId = 4,
                    clicked = clickedButton.intValue == 4,
                    onClick = {
                        clickedButton.intValue = it
                    })
            }
        }
    }

    @Composable
    fun ButtonMath(
        modifier: Modifier = Modifier,
        imageId: Int,
        clicked: Boolean = false,
        buttonId: Int = -1,
        onClick: (Int) -> Unit = {}
    ) {
        Box(
            modifier = modifier
                .background(
                    color = if (clicked) Color(0xFFEF8683) else Color.White,
                    shape = RoundedCornerShape(1000.dp)
                )
                .clickable {
                    onClick(buttonId)
                }
        ) {
            Icon(
                painter = painterResource(id = imageId),
                contentDescription = "",
                tint = if (!clicked) Color(0xFFEF8683)
                else Color.White
            )
        }

    }
}
