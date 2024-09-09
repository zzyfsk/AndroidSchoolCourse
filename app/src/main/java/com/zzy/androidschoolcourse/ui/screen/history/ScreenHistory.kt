package com.zzy.androidschoolcourse.ui.screen.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
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
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameState
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameView
import com.zzy.androidschoolcourse.ui.screen.online.ChatContent
import com.zzy.component.box.AlphaBox

class ScreenHistory(private val fileName: String) : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: HistoryViewModel = rememberScreenModel {
            HistoryViewModel(context)
        }
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = fileName) {
            viewModel.init(fileName)
        }
        Surface(modifier = Modifier.fillMaxWidth()) {
            when (viewModel.showType) {
                1 -> {
                    Column {
                        ViewMine(
                            modifier = Modifier.fillMaxWidth(),
                            gameState = viewModel.currentState1,
                            current = viewModel.current1,
                            previous = { viewModel.previous1() },
                            next = { viewModel.next1() }
                        )
                    }
                }

                2 -> {
                    Column {
                        ViewMine(
                            modifier = Modifier.fillMaxWidth(),
                            gameState = viewModel.currentState2,
                            current = viewModel.current2,
                            previous = { viewModel.previous2() },
                            next = { viewModel.next2() }
                        )
                        ViewOppose(
                            modifier = Modifier.fillMaxWidth(),
                            gameState = viewModel.currentState1,
                            current = viewModel.current1,
                            previous = { viewModel.previous1() },
                            next = { viewModel.next1() }
                        )
                        Button(onClick = { viewModel.showText = true }) {
                            Text(text = "展示历史记录")
                        }
                    }
                }
            }
        }
        if (viewModel.showText) {
            HistoryText(modifier = Modifier.fillMaxSize(), chatList = viewModel.chatList)
        }
        BackHandler {
            if (viewModel.showText) viewModel.showText = false
            else navigator.pop()
        }
    }

    @Composable
    fun ViewMine(
        modifier: Modifier = Modifier,
        gameState: TwentyFourGameState,
        current: Int,
        previous: () -> Unit = {},
        next: () -> Unit = {},
    ) {
        TwentyFourGameView(gameViewModel = gameState)
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { previous() }) {
                Text(text = "上一步")
            }
            Text(text = current.toString())
            Button(onClick = { next() }) {
                Text(text = ("下一步"))
            }
        }
    }

    @Composable
    fun ViewOppose(
        modifier: Modifier = Modifier,
        gameState: TwentyFourGameState,
        current: Int,
        previous: () -> Unit = {},
        next: () -> Unit = {},
    ) {
        TwentyFourGameView(gameViewModel = gameState)
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { previous() }) {
                Text(text = "上一步")
            }
            Text(text = current.toString())
            Button(onClick = { next() }) {
                Text(text = ("下一步"))
            }
        }
    }

    @Composable
    fun HistoryText(
        modifier: Modifier,
        chatList: List<ChatContent>
    ) {
        AlphaBox(modifier = modifier, alpha = 0.8f) {
            Column {
                Spacer(modifier = Modifier.requiredHeight(50.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(chatList) { chatItem ->
                        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, bottom = 5.dp)) {
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