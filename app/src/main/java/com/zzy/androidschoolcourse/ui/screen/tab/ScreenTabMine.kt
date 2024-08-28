package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.screen.BoxWave
import com.zzy.androidschoolcourse.ui.screen.DrawCircleWithImage
import com.zzy.androidschoolcourse.ui.theme.MainColor
import com.zzy.androidschoolcourse.ui.theme.ResIcon
import com.zzy.base.http.bean.UserDetailHttp
import com.zzy.base.koin.account.AccountViewModel
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
                TopInformation(
                    modifier = Modifier.fillMaxWidth(),
                    navigator = navigator,
                    isLogin = accountViewModel.isLogin()
                )
            }

        }
    }

    @Composable
    fun TopInformation(
        modifier: Modifier = Modifier,
        navigator: Navigator,
        isLogin: Boolean = false
    ) {
        BoxWave(
            modifier = modifier.fillMaxWidth(),
            height = 80.dp,
            color = MainColor.title
        ) {
            InformationBox(
                modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun InformationBox(
        modifier: Modifier = Modifier,
        avatar: Int = ResIcon.nonUserAvatar,
        user: UserDetailHttp = UserDetailHttp.testUser
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            DrawCircleWithImage(
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 10.dp,
                    end = 5.dp,
                    bottom = 10.dp
                ), imagePainter = painterResource(id = avatar)
            )
            Column(Modifier.weight(1f)) {
                Text(text = user.name, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.requiredHeight(1.dp))
                Text(text = user.signature, color = Color.White)
            }
            Icon(painter = painterResource(ResIcon.chevronRight), contentDescription = "right", tint = Color.White)
        }
    }
}