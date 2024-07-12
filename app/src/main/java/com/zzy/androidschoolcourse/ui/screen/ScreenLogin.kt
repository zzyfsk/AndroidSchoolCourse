package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zzy.androidschoolcourse.ui.screen.game.ScreenGameHiToRi
import com.zzy.androidschoolcourse.ui.screen.online.ScreenOnline


class ScreenLogin: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column (modifier = Modifier.fillMaxSize()){
            Text(text = "This is Login Screen")
            Column {
                Button(onClick = { navigator?.push(ScreenGameHiToRi()) }) {
                    Text(text = "单人游戏")
                }
                Button(onClick = { navigator?.push(ScreenOnline()) }) {
                    Text(text = "多人游戏")
                }
                Button(onClick = { navigator?.push(ScreenText2()) }) {
                    Text(text = "ktor Server")
                }
                Button(onClick = { navigator?.push(ScreenZlh()) }) {
                    Text(text = "zlh's screen")
                }

            }
        }
    }
}

