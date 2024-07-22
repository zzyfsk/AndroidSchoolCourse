package com.zzy.androidschoolcourse.ui.screen.online

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGame
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameView

class ScreenFuTaRi(val ip: String, private val port: Int = 5123, val right: GameRight) : Screen {
    private var viewModel: FuTaRiViewModel? = null

    @Composable
    override fun Content() {
        viewModel = rememberScreenModel { FuTaRiViewModel(ip, port, right) }
        LaunchedEffect(key1 = Unit) {
            viewModel?.init()
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "多人游戏界面")
            BarTop()
            GameOppose()
            HorizontalDivider(thickness = 1.dp)
            GameMe()
        }

    }

    @Composable
    fun BarTop(modifier: Modifier = Modifier) {

    }

    @Composable
    fun GameOppose() {
        viewModel?.opposeState?.let { TwentyFourGameView(it) }
    }

    @Composable
    fun GameMe() {
        TwentyFourGame(win = { }, click = {
            viewModel?.gameState = it
            viewModel?.sendGameState()
        }, initNumber = "1111")
    }
}