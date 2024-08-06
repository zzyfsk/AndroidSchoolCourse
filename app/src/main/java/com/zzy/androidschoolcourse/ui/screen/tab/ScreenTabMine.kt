package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zzy.androidschoolcourse.R
import com.zzy.base.koin.account.AccountViewModel
import com.zzy.login.ui.screen.login.ScreenLogin
import org.koin.androidx.compose.koinViewModel

object ScreenTabMine : Tab {
    private fun readResolve(): Any = ScreenTabMain
    override val options: TabOptions
        @Composable
        get() {
            val title = "我的"
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.person))

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent!!
        val accountViewModel: AccountViewModel = koinViewModel()

        Box(modifier = Modifier.fillMaxSize()) {
            Column {

                if (!accountViewModel.isLogin()) {
                    Text(text = "This is Mine Screen")
                    Button(onClick = { navigator.push(ScreenLogin()) }) {
                        Text(text = "login")
                    }
                } else {
                    Text(text = "你好 ${accountViewModel.user().name}")
                    Button(onClick = { accountViewModel.logout() }) {
                        Text(text = "logout")
                    }
                }
            }

        }
    }
}