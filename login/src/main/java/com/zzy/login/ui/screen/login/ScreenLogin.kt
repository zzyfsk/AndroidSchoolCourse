package com.zzy.login.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.base.bean.theme.ThemeViewModel
import com.zzy.component.box.MaskAnimModel
import com.zzy.component.box.MaskBox
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ScreenLogin : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val themeViewModel: ThemeViewModel = koinViewModel()
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
                Button(onClick = {
                    maskAnimActive(MaskAnimModel.SHRINK, 0f, 0f)
                }) {
                    Text(text = "icon")
                }
                Logo()
                TextFields()
            }
        }
    }

    @Composable
    fun Logo() {

    }

    @Composable
    fun TextFields() {
        val viewModel = rememberScreenModel {
            LoginViewModel()
        }
        TextField(
            value = viewModel.account,
            onValueChange = viewModel.onAccountChange,
            leadingIcon = { Text("账号") },
            maxLines = 1
        )
        TextField(
            value = viewModel.password,
            onValueChange = viewModel.onPasswordChange,
            leadingIcon = { Text("密码") },
            maxLines = 1
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
            Button(modifier = Modifier.padding(10.dp).weight(1f),onClick = { }) {
                Text(text = "登录")
            }
            Button(modifier = Modifier.padding(10.dp).weight(1f),onClick = { }) {
                Text(text = "注册")
            }
        }
    }
}