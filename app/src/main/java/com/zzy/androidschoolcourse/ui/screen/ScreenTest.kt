package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        val modifier = Modifier
        Column(modifier = Modifier.fillMaxSize()) {
            Text("This is Test Screen")
            Row(modifier = modifier.weight(1f)) {
                ButtonMath(imageId = R.drawable.add)
            }
        }
    }

    @Composable
    fun ButtonMath(
        modifier: Modifier = Modifier,
        imageId: Int,
        clicked: Boolean = false,
        onClick: (Unit) -> Int = { 0 }
    ) {
        Box(
            modifier = modifier
                .background(color = if (!clicked) Color(0xFFEF8683) else Color.White)
                .clip(RoundedCornerShape(100.dp))
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
