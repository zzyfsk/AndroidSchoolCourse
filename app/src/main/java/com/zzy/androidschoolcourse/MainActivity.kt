package com.zzy.androidschoolcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.zzy.androidschoolcourse.ui.screen.tab.ScreenTab
import com.zzy.androidschoolcourse.ui.theme.AndroidSchoolCourseTheme
import com.zzy.base.koin.account.appModule
import com.zzy.base.koin.theme.ThemeViewModel
import com.zzy.base.koin.theme.themeModule
import com.zzy.component.toast.Toast
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalVoyagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContent {
            val viewModel:ThemeViewModel = koinViewModel()
            AndroidSchoolCourseTheme(theme = viewModel.getTheme()) {
                Navigator(screen = ScreenTab()){
                    SlideTransition(navigator = it,disposeScreenAfterTransitionEnd = true)
                }
//                Toast(message = "aaadadadada")
            }
        }
    }

    private fun init(){
        setupKoin()
    }

    private fun setupKoin(){
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
            modules(themeModule)

        }
    }
}