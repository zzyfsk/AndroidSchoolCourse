package com.zzy.androidschoolcourse.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameState
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameView

class ScreenHistory(private val fileName: String) : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: HistoryViewModel = rememberScreenModel {
            HistoryViewModel(context)
        }
        LaunchedEffect(key1 = fileName) {
            viewModel.init(fileName)
        }
        Surface {
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
                    }
                }
            }
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
}