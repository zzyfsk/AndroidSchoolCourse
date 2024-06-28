package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zzy.androidschoolcourse.ui.screen.game.ScreenGameHiToRi


class ScreenLogin: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column (modifier = Modifier.fillMaxSize()){
            Text(text = "This is Login Screen")
            Row {
                Button(onClick = { navigator?.push(ScreenGameHiToRi()) }) {
                    Text(text = "next screen")
                }
                Button(onClick = { navigator?.push(ScreenText2()) }) {
                    Text(text = "yyx's Screen")
                }
                Button(onClick = { navigator?.push(ScreenZlh()) }) {
                    Text(text = "zlh's screen")
                }
            }
            Canvas(modifier = Modifier) {


            }
        }
    }
}

