package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.theme.NumberPressed
import com.zzy.androidschoolcourse.ui.theme.NumberUnpressed

class ScreenGame : Screen {
    @Composable
    override fun Content() {
        val clickedButton = remember {
            mutableIntStateOf(0)
        }
        val modifier = Modifier
        Column(modifier = Modifier.fillMaxSize()) {
            Text("This is Test Screen")
            ButtonsNumber()

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
    fun ButtonsNumber() {
        Column(Modifier.fillMaxWidth()) {
            Row {
                ButtonNumber(number = 1, clicked = true)
                ButtonNumber(number = 4, clicked = false)
            }
            Row {
                ButtonNumber(number = 12, clicked = false)
                ButtonNumber(number = 7, clicked = false)
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
        Surface(
            modifier = modifier,
            onClick = { onClick(buttonId) },
            shape = RoundedCornerShape(100),
            color = if (clicked) Color(0xFFEF8683) else Color.White
        ) {
            Icon(
                painter = painterResource(id = imageId),
                contentDescription = "",
                tint = if (!clicked) Color(0xFFEF8683)
                else Color.White
            )
        }
    }

    @Composable
    fun ButtonNumber(
        modifier: Modifier = Modifier,
        number: Int,
        clicked: Boolean,
        buttonId: Int = -1,
        onClick: () -> Unit = {},
    ) {
        Surface(
            modifier = modifier
                .size(100.dp, 100.dp)
                .padding(10.dp),
            onClick = { onClick() },
            color = if (clicked) NumberPressed else NumberUnpressed,
            shape = RoundedCornerShape(10.dp),
        ) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                Text(text = number.toString(), fontSize = 30.sp, textAlign = TextAlign.Center)
            }
        }
    }
}
