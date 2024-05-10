package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import androidx.compose.foundation.layout.padding


class ScreenLogin: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column {
            Text(text = "This is Login Screen")
            Row {
                Button(onClick = { navigator?.push(ScreenTest()) }) {
                    Text(text = "next screen")
                }
                Button(onClick = { navigator?.push(zlhScreen()) }) {
                    Text(text = "zlh's screen")
                }
            }

        }
    }
}

