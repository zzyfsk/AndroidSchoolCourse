package com.zzy.androidschoolcourse.ui.screen.online

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.b_koin.user.UserOnlyKoinViewModel
import com.zzy.component.toast.Toast
import com.zzy.component.toast.ToastWait
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ScreenOnline : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val viewModel = rememberScreenModel {
            OnlineSearchViewModel()
        }
        val accountViewModel: UserOnlyKoinViewModel = koinViewModel()
        val navigator = LocalNavigator.current
        LaunchedEffect(Unit) {
            viewModel.start(context, deviceName = accountViewModel.getAccountName())
        }

        fun finish() {
            viewModel.finish()
            navigator?.pop()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            BarTitle(onFinish = { finish() })
            Row {
                Button(onClick = { viewModel.find() }) {
                    Text(text = "find")
                }
                Button(onClick = {
                    navigator?.replace(
                        ScreenFuTaRi(
                            ip = "116.198.234.250",
                            right = GameRight.Command
                        )
                    )
                }) {
                    Text(text = "连接远程服务器 房主")
                }
                Button(onClick = {
                    navigator?.replace(
                        ScreenFuTaRi(
                            ip = "116.198.234.250",
                            right = GameRight.Client
                        )
                    )
                }) {
                    Text(text = "连接远程服务器 客户")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                items(viewModel.deviceList) {
                    if (it.ip != viewModel.ip) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            HorizontalDivider()
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.connect(it.ip, onConfirm = {
                                            CoroutineScope(Dispatchers.Default).launch {
                                                Thread.sleep(500)
                                                navigator?.replace(
                                                    ScreenFuTaRi(
                                                        ip = it.ip,
                                                        right = GameRight.Client
                                                    )
                                                )
                                            }
                                        })
                                    }, text = it.toString()
                            )
                        }
                    }
                }
            }
        }
        if (viewModel.showDialog) DialogOnline(onConfirm = {
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.sendResult(true)
                Thread.sleep(500)
                viewModel.finish()
            }
            scope.launch {
                Thread.sleep(600)
                navigator?.replace(
                    ScreenFuTaRi(
                        ip = viewModel.ip,
                        right = GameRight.Command,
                        name = accountViewModel.getAccountName()
                    )
                )
            }
        }, onDismiss = {
            viewModel.sendResult(false)
        })


        BackHandler {
            if (viewModel.state == OnlineSearchState.None) finish()
        }

        if (viewModel.state == OnlineSearchState.Search) {
            ToastWait()
        }
        if (viewModel.state == OnlineSearchState.Finish) {
            viewModel.stateFinish()
            Toast(message = "搜索完成")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BarTitle(modifier: Modifier = Modifier, onFinish: () -> Unit) {
        TopAppBar(
            modifier = modifier.fillMaxWidth(),
            title = { Text(text = "搜索") },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(100))
                        .clickable {
                            onFinish()
                        },
                    imageVector = ImageVector.vectorResource(id = R.drawable.back),
                    contentDescription = ""
                )
            }

        )
    }

    @Composable
    fun DialogOnline(onDismiss: () -> Unit = {}, onConfirm: () -> Unit = {}) {
        val viewModel = rememberScreenModel {
            OnlineSearchViewModel()
        }
        AlertDialog(
            onDismissRequest = { viewModel.showDialog = false },
            confirmButton = {
                Text(
                    modifier = Modifier.clickable {
                        onConfirm()
                        viewModel.showDialog = false
                    },
                    text = "confirm"
                )
            },
            dismissButton = {
                Text(
                    text = "cancel"
                )
            },
            text = {
                Text(
                    modifier = Modifier.clickable {
                        onDismiss()
                        viewModel.showDialog = false
                    },
                    text = "a device wang to compete with you"
                )
            }
        )
    }

}