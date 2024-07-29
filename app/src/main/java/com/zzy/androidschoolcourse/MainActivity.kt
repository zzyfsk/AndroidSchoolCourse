package com.zzy.androidschoolcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.zzy.androidschoolcourse.ui.screen.tab.ScreenTab
import com.zzy.androidschoolcourse.ui.theme.AndroidSchoolCourseTheme
import com.zzy.login.bean.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalVoyagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSchoolCourseTheme {
                Navigator(screen = ScreenTab()){
                    SlideTransition(navigator = it,disposeScreenAfterTransitionEnd = true)
                }
            }
        }
        init()
    }

    private fun init(){
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

}