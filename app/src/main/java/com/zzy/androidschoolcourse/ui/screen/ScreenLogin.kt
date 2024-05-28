package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator


class ScreenLogin: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column {
            Text(text = "This is Login Screen")
            Row {
                Button(onClick = { navigator?.push(ScreenGame()) }) {
                    Text(text = "next screen")
                }
//                Button(onClick = { navigator?.push(ScreenTest()) }) {
//                    Text(text = "zlh's screen")
//                }
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

