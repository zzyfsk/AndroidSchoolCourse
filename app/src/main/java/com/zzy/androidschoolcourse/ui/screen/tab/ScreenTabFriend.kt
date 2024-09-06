package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zzy.androidschoolcourse.R
import com.zzy.b_koin.user.UserOnlyKoinViewModel
import com.zzy.base.http.bean.UserDetailHttp
import org.koin.androidx.compose.koinViewModel

object ScreenTabFriend : Tab {
    private fun readResolve(): Any = ScreenTabMain
    override val options: TabOptions
        @Composable
        get() {
            val title = "好友"
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.group))

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
        val accountViewModel:UserOnlyKoinViewModel = koinViewModel()
        val tabNavigator = LocalTabNavigator.current
        LaunchedEffect(key1 = accountViewModel.user.name) {
            if (accountViewModel.user.id != 0L) accountViewModel.getAccountName()
        }
        if (accountViewModel.user.token.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(accountViewModel.friendList) { friend ->
                    FriendItem(friend = friend)
                }
            }
        } else {
            UnLogin(navigator = tabNavigator)
        }
    }

    @Composable
    fun FriendItem(avatar: Int = com.zzy.base.R.drawable.logo, friend: UserDetailHttp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = avatar), contentDescription = friend.name)
            Spacer(modifier = Modifier.requiredWidth(10.dp))
            Text(text = friend.name)
            Spacer(modifier = Modifier.requiredWidth(5.dp))
            Text(text = friend.signature)
        }
    }

    @Composable
    fun UnLogin(navigator: TabNavigator) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "请先登录以查看好友")
            Button(onClick = { navigator.current = ScreenTabMine }) {
                Text(text = "前往我的页面")
            }
        }
    }
}