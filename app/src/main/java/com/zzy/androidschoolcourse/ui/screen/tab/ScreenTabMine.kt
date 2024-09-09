package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
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
import com.zzy.b_koin.user.UserOnlyKoinViewModel
import com.zzy.base.http.bean.UserDetailHttp
import com.zzy.component.button.FloatButton
import com.zzy.login.ui.screen.edit.ScreenEdit
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
        val context = LocalContext.current
        val viewModel = rememberScreenModel {
            TabViewModel(context = context)
        }
        val accountViewModel: UserOnlyKoinViewModel = koinViewModel()
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Column(modifier = Modifier.requiredHeight(80.dp)) {
                    TopInformation(
                        modifier = Modifier.fillMaxWidth(),
                        navigator = navigator,
                        isLogin = accountViewModel.isLogin(),
                        user = accountViewModel.user
                    )
                }
                Spacer(modifier = Modifier.requiredHeight(80.dp))
                MiddleInformation(
                    modifier = Modifier.fillMaxWidth(),
                    middleInformation = viewModel.information
                )
                Spacer(modifier = Modifier.requiredHeight(30.dp))
//                if(accountViewModel.isLogin()){
                    BottomButton(modifier = Modifier.fillMaxWidth())
//                }
            }

        }
        TestFloatButton(devLogin = {
            accountViewModel.devLogin()
            println(accountViewModel.user.token)
        })
        LaunchedEffect(key1 = Unit) {
            viewModel.updateInformation(
                updateFriend = {
                    accountViewModel.friendList.size
                },
                updateApply = {
                    return@updateInformation 0
                },
                updateWill = {
                    return@updateInformation 0
                },
                updateScore = {
                    accountViewModel.user.score
                })
        }
    }
}

@Composable
fun TopInformation(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    isLogin: Boolean = false,
    user: UserDetailHttp
) {
    BoxWave(
        modifier = modifier.fillMaxWidth(),
        height = 80.dp,
        color = MainColor.title
    ) {
        ComponentInformation(
            modifier
                .fillMaxWidth()
                .clickable {
                    if (isLogin) {
                        //TODO
                    } else {
                        navigator.push(ScreenLogin())
                    }
                }, user = user
        )
    }
}

@Composable
fun ComponentInformation(
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
            Text(
                text = user.name.ifBlank { "未登录" },
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.requiredHeight(1.dp))
            Text(text = user.signature.ifBlank { "使用账号功能，请先登录" }, color = Color.White)
        }
        Icon(
            painter = painterResource(ResIcon.chevronRight),
            contentDescription = "right",
            tint = Color.White
        )

    }
}

@Composable
fun TestFloatButton(devLogin: () -> Unit) {
    var isExpand by remember { mutableStateOf(false) }
    FloatButton(
        isExpand = isExpand,
        expandContent1 = {
            Button(onClick = {
                devLogin()
            }) {
                Text(text = "使用测试用户")
            }
        },
        expandContent2 = { Text(text = "2") },
        expandContent3 = { Text(text = "3") },
        onClick = { isExpand = !isExpand }
    )
}

@Composable
fun MiddleInformation(modifier: Modifier = Modifier, middleInformation: MineMiddleInformation) {
    Row(modifier = modifier) {
        MiddleInformationItem(
            modifier = Modifier
                .weight(1f), number = middleInformation.friendNumber, information = "好友"
        )
        MiddleInformationItem(
            modifier = Modifier
                .weight(1f), number = middleInformation.applyNumber, information = "申请"
        )
        MiddleInformationItem(
            modifier = Modifier
                .weight(1f), number = middleInformation.willNumber, information = "充数"
        )
        MiddleInformationItem(
            modifier = Modifier
                .weight(1f), number = middleInformation.score, information = "积分"
        )
    }
}

@Composable
fun MiddleInformationItem(
    modifier: Modifier = Modifier,
    number: Int,
    information: String
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = number.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1
            )
            Text(text = information, fontSize = 18.sp)
        }
    }
}

@Composable
fun BottomButton(modifier: Modifier) {
    val navigator = LocalNavigator.currentOrThrow.parent!!
    BottomItem(
        modifier = modifier,
        icon = com.zzy.base.R.drawable.manage_accounts,
        color = Color.Gray,
        text = "管理账户"
    ) {
        navigator.push(ScreenEdit())
    }
}

@Composable
fun BottomItem(
    modifier: Modifier,
    icon: Int,
    color:Color,
    text: String,
    onClick: () -> Unit
) {
    Row(modifier = modifier
        .height(30.dp)
        .fillMaxWidth()
        .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.padding(horizontal = 10.dp),
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = color
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

data class MineMiddleInformation(
    val friendNumber: Int = 0,
    val applyNumber: Int = 0,
    val willNumber: Int = 0,
    val score: Int = 0
)