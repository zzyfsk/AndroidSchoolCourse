package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class ScreenLogin: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Text(text = "This is Login Screen")
        Button(onClick = { navigator?.push(ScreenTest()) }) {
            Text(text = "next screen")
        }
    }
}