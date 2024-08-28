package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
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
                Spacer(modifier = Modifier.requiredHeight(250.dp))
            }
        }
    }
}