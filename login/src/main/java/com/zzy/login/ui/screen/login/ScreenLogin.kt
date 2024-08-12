package com.zzy.login.ui.screen.login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zzy.base.koin.account.AccountViewModel
import com.zzy.base.koin.theme.Theme
import com.zzy.base.koin.theme.ThemeViewModel
import com.zzy.component.box.MaskAnimModel
import com.zzy.component.box.MaskBox
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ScreenLogin : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val themeViewModel: ThemeViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
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
                TextFields(navigator = navigator)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(
        theme: Theme,
        maskAnimActive: (MaskAnimModel, Float, Float) -> Unit
    ) {
        val icon = when (theme) {
            Theme.Normal -> com.zzy.base.R.drawable.light_mode
            Theme.Dark -> com.zzy.base.R.drawable.dark_mode
        }
        TopAppBar(title = { Text(text = "登录") }, actions = {
            Icon(
                modifier = Modifier.clickable {
                    maskAnimActive(MaskAnimModel.SHRINK, 0f, 0f)
                },
                painter = painterResource(id = icon),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.requiredWidth(5.dp))
        })
    }

    @Composable
    fun Logo(modifier: Modifier = Modifier) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(id = com.zzy.base.R.drawable.logo),
            contentDescription = "logo"
        )
    }

    @Composable
    fun TextFields(navigator: Navigator) {
        val viewModel = rememberScreenModel {
            LoginViewModel()
        }
        val accountViewModel: AccountViewModel = koinViewModel()
        TextField(
            value = viewModel.account,
            onValueChange = viewModel.onAccountChange,
            leadingIcon = { Text("账号") },
            placeholder = { Text(text = accountViewModel.getAccount()) },
            maxLines = 1
        )
        TextField(
            value = viewModel.password,
            onValueChange = viewModel.onPasswordChange,
            leadingIcon = { Text("密码") },
            maxLines = 1
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Button(modifier = Modifier
                .padding(10.dp)
                .weight(1f),
                onClick = {
                    accountViewModel.login(
                        viewModel.account.ifEmpty { accountViewModel.getAccount() },
                        viewModel.password
                    )
                }) {
                Text(text = "登录")
            }
            Button(modifier = Modifier
                .padding(10.dp)
                .weight(1f), onClick = { }) {
                Text(text = "注册")
            }
        }
    }
}