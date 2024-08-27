package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.component.background.CanvasWave

class ScreenTest : Screen {
    @Composable
    override fun Content() {
        val widthX = LocalConfiguration.current.screenWidthDp * LocalDensity.current.density
        var width by remember {
            mutableFloatStateOf(widthX)
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column (modifier = Modifier.fillMaxSize()){
                Column {
                    CanvasWave(modifier = Modifier.fillMaxWidth(), width = width)
                }
                Spacer(modifier = Modifier.requiredHeight(100.dp))
                TextField(value = width.toString(), onValueChange = {
                    width = try {
                        it.toFloat()
                    }catch (e:NumberFormatException){
                        println(e.localizedMessage)
                        widthX
                    }
                })
            }
        }
    }
}