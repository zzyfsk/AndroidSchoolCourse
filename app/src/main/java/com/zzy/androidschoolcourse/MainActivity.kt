package com.zzy.androidschoolcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.zzy.androidschoolcourse.ui.screen.ScreenLogin
import com.zzy.androidschoolcourse.ui.theme.AndroidSchoolCourseTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalVoyagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSchoolCourseTheme {
                Navigator(screen = ScreenLogin()){
                    SlideTransition(navigator = it,disposeScreenAfterTransitionEnd = true)
                }
            }
        }
    }
}