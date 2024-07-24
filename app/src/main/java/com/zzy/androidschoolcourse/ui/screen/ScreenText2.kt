package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen

class ScreenText2 : Screen {
    @Composable
    override fun Content() {
        var show by remember {
            mutableStateOf(false)
        }

        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Text(text = "我的测试页面")
            Button(onClick = { show = true }) {
                Text(text = "win win win")
            }
        }

        if (show) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White.copy(alpha = 0.5f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Row {
                        Button(onClick = { show = false }) {
                            Text(text = "true")
                        }
                        Button(onClick = { }) {
                            Text(text = "false")
                        }
                    }
                }
            }
        }


    }
}