package com.zzy.androidschoolcourse.ui.screen.online

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.zzy.androidschoolcourse.net.server.find.ServerFind
import com.zzy.androidschoolcourse.util.IPUtil

class ScreenOnline : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel = rememberScreenModel {
            OnlineSearchViewModel()
        }
        val navigator = LocalNavigator.current
        LaunchedEffect(Unit) {

        }
        Column(modifier = Modifier.fillMaxSize()) {
            BarTitle()
            Button(onClick = { viewModel.start(context, navigate = {navigator?.push(ScreenFuTaRi(ip = viewModel.ip))}) }) {
                Text(text = "Server Run")
            }
            Button(onClick = { viewModel.end() }) {
                Text(text = "Server stop")
            }
            Button(onClick = { viewModel.clientStart() }) {
                Text(text = "client Run")
            }
            Button(onClick = { viewModel.end() }) {
                Text(text = "client stop")
            }
            LazyColumn {
                items(viewModel.deviceList) {
                    Text(modifier = Modifier.clickable {
                        viewModel.clientConnect(it, 5124, navigate = {
                            navigator?.push(ScreenFuTaRi(ip = it))
                        })
                    }, text = it)
                }
            }
        }
        if (viewModel.showDialog) DialogOnline(onConfirm = {
            ServerFind.connect()
            navigator?.push(ScreenFuTaRi(ip = viewModel.ip))
        })
        BackHandler {
            viewModel.end()
            navigator?.pop()
        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BarTitle(modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current
        TopAppBar(
            modifier = modifier.fillMaxWidth(),
            title = { Text(text = "搜索") },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(100))
                        .clickable {
                            navigator?.pop()
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
        val navigator = LocalNavigator.current
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