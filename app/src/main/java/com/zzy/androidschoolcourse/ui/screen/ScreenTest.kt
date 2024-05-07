package com.zzy.androidschoolcourse.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class ScreenTest:Screen {
    @Composable
    override fun Content() {
        Text("This is Test Screen")
    }

}