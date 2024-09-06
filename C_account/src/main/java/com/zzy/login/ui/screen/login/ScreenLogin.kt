package com.zzy.login.ui.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zzy.b_koin.user.AccountHttpState
import com.zzy.b_koin.user.UserKoinViewModel
import com.zzy.base.koin.theme.Theme
import com.zzy.base.koin.theme.ThemeViewModel
import com.zzy.component.box.MaskAnimModel
import com.zzy.component.box.MaskBox
import com.zzy.component.toast.Toast
import com.zzy.component.toast.ToastWait
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ScreenLogin : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val themeViewModel: ThemeViewModel = koinViewModel()
        val viewModel = rememberScreenModel {
            LoginViewModel()
        }
        val accountViewModel: UserKoinViewModel = koinViewModel()

        MaskBox(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            maskComplete = {
                scope.launch {
                    themeViewModel.changeTheme()
                }
            },
            animFinish = {
            }) { maskAnimActive ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar(theme = themeViewModel.getTheme(), maskAnimActive = maskAnimActive)
                Logo()
                TextFields(
                    viewModel,
                    getAccount = { accountViewModel.getAccount() },
                    login = { account, password ->
                        accountViewModel.login(account, password,viewModel.onHttpStateChange)
                    },
                    register = { account,password->
                        accountViewModel.register(account, password)
                    }
                )
            }
        }
        Toast(httpState = viewModel.httpState, stateReset = {viewModel.stateReset()})
        LaunchedEffect(key1 = viewModel.httpState) {
            println(viewModel.httpState)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(
        theme: Theme,
        maskAnimActive: (MaskAnimModel, Float, Float) -> Unit
    ) {
        var componentPosition by remember { mutableStateOf(Pair(0f, 0f)) }

        val icon = when (theme) {
            Theme.Normal -> com.zzy.base.R.drawable.light_mode
            Theme.Dark -> com.zzy.base.R.drawable.dark_mode
        }
        TopAppBar(title = { Text(text = "登录") }, actions = {
            Icon(
                modifier = Modifier
                    .clickable {
                        maskAnimActive(
                            MaskAnimModel.SHRINK,
                            componentPosition.first,
                            componentPosition.second
                        )
                    }
                    .onGloballyPositioned { coordinates ->
                        componentPosition = Pair(
                            coordinates.positionInRoot().x + coordinates.size.width / 2,
                            coordinates.positionInRoot().y + coordinates.size.height / 2
                        )
                    },// 试试做成拓展函数
                painter = painterResource(id = icon),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.requiredWidth(5.dp))
        })
    }

    @Composable
    fun Logo(modifier: Modifier = Modifier) {
        Image(
            modifier = modifier
                .size(200.dp)
                .padding(top = 5.dp),
            painter = painterResource(id = com.zzy.base.R.drawable.logo),
            contentDescription = "logo"
        )
    }

    @Composable
    fun TextFields(
        viewModel: LoginViewModel,
        getAccount: () -> String,
        login: (String, String) -> Unit,
        register: (String, String) -> Unit
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 5.dp),
            value = viewModel.account,
            onValueChange = viewModel.onAccountChange,
            leadingIcon = { Text("账号") },
            placeholder = { Text(text = getAccount()) },
            maxLines = 1
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 5.dp),
            value = viewModel.password,
            onValueChange = viewModel.onPasswordChange,
            leadingIcon = { Text("密码") },
            maxLines = 1
        )
        AnimatedVisibility(
            visible = viewModel.pageState == StateLogin.Register,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(bottom = 5.dp),
                value = viewModel.password2,
                onValueChange = viewModel.onPassword2Change,
                leadingIcon = { Text(text = "确认") }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Button(modifier = Modifier
                .padding(10.dp)
                .weight(1f),
                onClick = {
                    if (viewModel.pageState == StateLogin.Register) {
                        viewModel.pageState = StateLogin.Login
                    } else {
                        login(
                            viewModel.account.ifEmpty { getAccount() },
                            viewModel.password
                        )
                    }
                }) {
                Text(text = "登录")
            }
            Button(modifier = Modifier
                .padding(10.dp)
                .weight(1f), onClick = {
                if (viewModel.pageState == StateLogin.Login) {
                    viewModel.pageState = StateLogin.Register
                } else {
                    if (viewModel.checkPassword()) {
                        register(
                            viewModel.account.ifEmpty { getAccount() },
                            viewModel.password
                        )
                    }
                }
            }) {
                Text(text = "注册")
            }
        }
    }

    @Composable
    fun Toast(httpState: AccountHttpState,stateReset:()->Unit){
        val message = when(httpState){
            AccountHttpState.None -> null
            AccountHttpState.Success -> "登录成功"
            AccountHttpState.Connecting -> null
            AccountHttpState.Fail -> "登录失败"
            AccountHttpState.Wrong -> "服务器错误"
        }
        val navigator = LocalNavigator.currentOrThrow
        when(httpState){
            AccountHttpState.Connecting->{
                ToastWait()
            }
            AccountHttpState.Success->{
                Toast(message = message)
                stateReset()
                navigator.pop()
            }
            else->{
                Toast(message = message)
                stateReset()
            }
        }
        if (httpState == AccountHttpState.Connecting) {
            ToastWait()
        }
        else{
            Toast(message = message)
            stateReset()
        }
        LaunchedEffect(key1 = httpState) {
            println(httpState)
        }
    }
}