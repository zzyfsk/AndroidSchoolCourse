package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.zzy.androidschoolcourse.ui.component.TabNavigationItem

class ScreenTab:Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(tab = ScreenTabMain,
            tabDisposable = {
                TabDisposable(navigator = it, tabs = listOf(
                    ScreenTabMain,
                    ScreenTabFriend,
                    ScreenTabHistory,
                    ScreenTabMine
                ))
            }){

            Scaffold (
                topBar = {
                    TopAppBar(title = { Text(text = "Main")})
                }    , content = { paddingValues->
                    Box(modifier = Modifier.padding(paddingValues)){
                        CurrentTab()
                    }
                }, bottomBar = {
                    NavigationBar {
                        TabNavigationItem(tab = ScreenTabMain)
                        TabNavigationItem(tab = ScreenTabFriend)
                        TabNavigationItem(tab = ScreenTabHistory)
                        TabNavigationItem(tab = ScreenTabMine)
                    }
                }
            )
        }
    }
}