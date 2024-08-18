package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.zzy.androidschoolcourse.ui.component.TabNavigationItem
import com.zzy.androidschoolcourse.ui.theme.AndroidSchoolCourseTheme
import com.zzy.base.koin.account.AccountViewModel
import com.zzy.base.koin.account.LoginHttpState
import com.zzy.base.koin.theme.Theme
import com.zzy.base.koin.theme.ThemeViewModel
import com.zzy.component.box.MaskAnimModel
import com.zzy.component.box.MaskBox
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ScreenTab : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val themeViewModel: ThemeViewModel = koinViewModel()
        val icon = when (themeViewModel.getTheme()) {
            Theme.Normal -> com.zzy.base.R.drawable.light_mode
            Theme.Dark -> com.zzy.base.R.drawable.dark_mode
        }
        val accountViewModel: AccountViewModel = koinViewModel()
        var componentPosition by remember { mutableStateOf(Pair(0f, 0f)) }
        Toast(accountViewModel = accountViewModel)
        MaskBox(modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            maskComplete = {
                scope.launch {
                    themeViewModel.changeTheme()
                }
            },
            animFinish = {}) { maskAnimActive ->
//            println("recompose")
            TabNavigator(
                tab = ScreenTabMain,
                tabDisposable = {
                    TabDisposable(
                        navigator = it, tabs = listOf(
                            ScreenTabMain,
                            ScreenTabFriend,
                            ScreenTabHistory,
                            ScreenTabMine
                        )
                    )
                }) {
                AndroidSchoolCourseTheme(theme = themeViewModel.getTheme()) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = { Text(text = "Main") }, actions = {
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            maskAnimActive(
                                                MaskAnimModel.SHRINK,
                                                componentPosition.first,
                                                componentPosition.second
                                            )
                                        }
                                        .getMiddlePosition {
                                            componentPosition = it
                                        }
                                    ,
                                    painter = painterResource(id = icon),
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.requiredWidth(5.dp))
                            })
                        },
                        content = { paddingValues ->
                            Box(modifier = Modifier.padding(paddingValues)) {
                                CurrentTab()
                            }
                        },
                        bottomBar = {
                            NavigationBar {
                                TabNavigationItem(tab = ScreenTabMain)
                                TabNavigationItem(tab = ScreenTabFriend)
                                TabNavigationItem(tab = ScreenTabHistory)
                                TabNavigationItem(tab = ScreenTabMine)
                            }
                        },
                    )
                }
            }
        }
    }

    @Composable
    fun Toast(accountViewModel: AccountViewModel) {
        when (accountViewModel.httpState) {
            LoginHttpState.None -> {

            }

            LoginHttpState.Loading -> TODO()
            LoginHttpState.LoginSuccess -> {
                com.zzy.component.toast.Toast(message = "登录成功")
                accountViewModel.httpState = LoginHttpState.None
            }

            LoginHttpState.LoginFail -> TODO()
            LoginHttpState.RegisterSuccess -> TODO()
            LoginHttpState.RegisterFai -> TODO()
        }
    }
}

fun Modifier.getMiddlePosition(result: (Pair<Float, Float>) -> Unit): Modifier {
    return this.onGloballyPositioned {
        result(Pair(it.positionInRoot().x+it.size.width/2, it.positionInRoot().y+it.size.height/2))
    }
}