package com.zzy.androidschoolcourse.ui.screen.online

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameSmall
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameSmallView
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameState
import com.zzy.b_koin.user.UserOnlyKoinViewModel
import com.zzy.component.box.AlphaBox
import com.zzy.component.toast.ToastWait
import org.koin.androidx.compose.koinViewModel

class ScreenFuTaRi(val ip: String, private val port: Int = 5123, val right: GameRight,val name:String = "") : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: FuTaRiViewModel =
            rememberScreenModel { FuTaRiViewModel(ip, port, right,name, context) }
        val accountViewModel: UserOnlyKoinViewModel = koinViewModel()
        LaunchedEffect(key1 = Unit) {
            viewModel.init()
//            viewModel.huTaRiState = FuTaRiState.UI
        }
        BackHandler {
            if (viewModel.showChat) {
                viewModel.showChat = false
            } else {
                viewModel.finish()
                navigator.pop()
            }
        }
        if (viewModel.huTaRiState == FuTaRiState.Socket || viewModel.huTaRiState == FuTaRiState.Socket) {
            ToastWait()
        }

        when (viewModel.huTaRiState) {
            FuTaRiState.Socket -> {}
            FuTaRiState.Data -> {}
            FuTaRiState.UI -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row {
                        Text(text = "多人游戏界面 ${viewModel.gameState.numbers}")
                    }
                    BarTop()
                    GameOppose(opposeState = viewModel.opposeState)
                    HorizontalDivider(thickness = 1.dp)
                    GameMe(viewModel = viewModel)
                }
            }

        }
        if (viewModel.showWin) {
            WinComponent(modifier = Modifier.fillMaxSize())
        }
        if (viewModel.showChat) {
            ChatComponent(
                modifier = Modifier.fillMaxSize(),
                chatList = viewModel.chatList,
                viewModel = viewModel,
                name = accountViewModel.getAccountName()
            )
        }

    }

    @Composable
    fun BarTop() {

    }

    @Composable
    fun GameOppose(opposeState: TwentyFourGameState) {
        TwentyFourGameSmallView(opposeState)
    }

    @Composable
    fun GameMe(viewModel: FuTaRiViewModel) {
        TwentyFourGameSmall(win = { viewModel.gameWin() }, click = { gameState ->
            viewModel.sendGameState(gameState)
            viewModel.recordState(1, gameState)
        }, gameState = viewModel.gameState, onChat = { viewModel.showChat = true })
    }

    @Composable
    fun WinComponent(modifier: Modifier = Modifier) {
        AlphaBox(modifier = modifier, alpha = 0.8f) {
            Column {
                Text(modifier = Modifier.weight(0.5f), text = "游戏结束")

            }
        }
    }

    @Composable
    fun ChatComponent(
        modifier: Modifier = Modifier,
        chatList: List<ChatContent> = listOf(),
        viewModel: FuTaRiViewModel,
        name: String
    ) {
        AlphaBox(modifier = modifier, alpha = 0.8f) {
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = viewModel.chatContent, onValueChange = {
                        viewModel.chatContent = it
                    })
                    Button(onClick = { viewModel.sendChat() }) {
                        Text(text = "发送")
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(chatList) { chatItem ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "${chatItem.name}:")
                            Spacer(modifier = Modifier.requiredWidth(5.dp))
                            Text(text = chatItem.content)
                        }
                    }
                }
            }

        }
    }
}

