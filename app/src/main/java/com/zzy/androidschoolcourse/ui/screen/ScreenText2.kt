package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

class ScreenText2 : Screen{
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
    @Composable
    fun ButtonNumber(
        modifier: Modifier = Modifier,
        imageId: Int,
        clicked: Boolean = false,
        buttonId: Int = -1,
        onClick: (Int) -> Unit = {}
    ) {
        Box(
            modifier = modifier
                .background(color = if (clicked) Color(0xFFEF8683) else Color.White)
                .clickable {
                    onClick(buttonId)
                }
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