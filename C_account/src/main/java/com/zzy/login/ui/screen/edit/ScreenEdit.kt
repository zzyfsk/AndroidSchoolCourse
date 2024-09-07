package com.zzy.login.ui.screen.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import com.zzy.b_koin.user.UserKoinViewModel
import com.zzy.base.koin.theme.Theme
import com.zzy.base.koin.theme.ThemeViewModel
import com.zzy.component.box.MaskAnimModel
import com.zzy.component.box.MaskBox
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ScreenEdit : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            EditViewModel()
        }
        val scope = rememberCoroutineScope()
        val theme:ThemeViewModel = koinViewModel()
        val accountViewModel:UserKoinViewModel = koinViewModel()
        MaskBox(modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            maskComplete = {
                scope.launch {
                    theme.changeTheme()
                }
            },
            animFinish = {}){ maskAnimActive->
            TopBar(theme = theme.getTheme(),maskAnimActive = maskAnimActive)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Logo()
                Spacer(modifier = Modifier.requiredHeight(10.dp))
                EditTexts(
                    onEdit = {
                        accountViewModel.update(
                            viewModel.account,
                            viewModel.password,
                            viewModel.password2,
                            viewModel.name,
                            viewModel.signature
                        )
                    }
                )
            }
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
        TopAppBar(title = { Text(text = "修改信息") }, actions = {
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
    fun EditTexts(
        onEdit:()->Unit
    ){
        val viewModel = rememberScreenModel {
            EditViewModel()
        }
        val space = 10.dp
        OutlinedTextField(
            modifier = Modifier.padding(bottom = space),
            value = viewModel.account,
            onValueChange = viewModel.onAccountChange,
            leadingIcon = { Text("账号") },
            maxLines = 1
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = space),
            value = viewModel.password,
            onValueChange = viewModel.onPasswordChange,
            leadingIcon = { Text("密码") },
            maxLines = 1
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = space),
            value = viewModel.password2,
            onValueChange = viewModel.onPassword2Change,
            leadingIcon = { Text(text = "确认") }
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = space),
            value = viewModel.name,
            onValueChange = viewModel.onNameChange,
            leadingIcon = { Text(text = "昵称") }
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = space),
            value = viewModel.signature,
            onValueChange = viewModel.onSignatureChange,
            leadingIcon = { Text(text = "签名") }
        )
        Spacer(modifier = Modifier.requiredHeight(space))
        Button(onClick = { onEdit() }) {
            Text(text = "修改")
        }
        Spacer(modifier = Modifier.requiredHeight(space))
        Text(text = "不更改请留空")
    }
}