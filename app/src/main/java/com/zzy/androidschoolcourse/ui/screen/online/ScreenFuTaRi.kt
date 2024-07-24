package com.zzy.androidschoolcourse.ui.screen.online

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGame
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameState
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameView

class ScreenFuTaRi(val ip: String, private val port: Int = 5123, val right: GameRight) : Screen {


    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: FuTaRiViewModel =
            rememberScreenModel { FuTaRiViewModel(ip, port, right, context) }
        LaunchedEffect(key1 = Unit) {
            viewModel.init()
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "多人游戏界面 ${viewModel.gameState.numbers}")
            BarTop()
            GameOppose(opposeState = viewModel.opposeState)
            HorizontalDivider(thickness = 1.dp)
            GameMe()
        }

        if (viewModel.showWin) {
            WinComponent()
        }

    }

    @Composable
    fun BarTop(modifier: Modifier = Modifier) {

    }

    @Composable
    fun GameOppose(opposeState: TwentyFourGameState) {
        TwentyFourGameView(opposeState)
    }

    @Composable
    fun GameMe() {
        val context = LocalContext.current
        val viewModel: FuTaRiViewModel =
            rememberScreenModel { FuTaRiViewModel(ip, port, right, context) }
        TwentyFourGame(win = { viewModel.gameWin() }, click = {
            viewModel.gameState = it
            viewModel.sendGameState()
        }, gameState = viewModel.gameState)
    }

    @Composable
    fun WinComponent(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White.copy(0.5f))
        ) {

        }
    }
}

