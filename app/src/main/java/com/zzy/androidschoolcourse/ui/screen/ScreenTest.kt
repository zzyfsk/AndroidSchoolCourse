package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.component.background.CanvasWave

class ScreenTest : Screen {
    @Composable
    override fun Content() {
        val widthX = LocalConfiguration.current.screenWidthDp
        val width = (widthX + 1).dp
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column {
                    CanvasWave(modifier = Modifier.fillMaxWidth(), height = 200.dp, width = width)
                }
                Spacer(modifier = Modifier.requiredHeight(15.dp))
                MiddleInformation()
            }
        }
    }

    @Composable
    fun MiddleInformation(modifier: Modifier = Modifier) {
        Row(modifier = modifier) {
            MiddleInformationItem(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp), number = 1, information = "好友"
            )
            MiddleInformationItem(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp), number = 1, information = "申请"
            )
            MiddleInformationItem(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp), number = 1, information = "不会"
            )
            MiddleInformationItem(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp), number = 1, information = "积分"
            )
        }
    }

    @Composable
    fun MiddleInformationItem(
        modifier: Modifier = Modifier,
        number: Int,
        information: String
    ) {
        Box(modifier = modifier) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = number.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = information, fontSize = 18.sp)
            }
        }
    }
}