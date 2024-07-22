package com.zzy.androidschoolcourse.ui.screen.online

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.net.socket.game.ServiceGame
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameButtonState
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class FuTaRiViewModel(private val ip: String, private val port: Int, private val right: GameRight) :
    ScreenModel {
        private val tag = "FuTaRiViewModel"
    private val serviceGame = ServiceGame()
    var opposeState by mutableStateOf(TwentyFourGameState())
    var gameState by mutableStateOf(TwentyFourGameState())


    fun sendGameState() {
        serviceGame.sendGameState(gameState, right)
    }

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            if (right == GameRight.Command) {
                serviceGame.serverStart()
                serviceGame.controllerStart(ip = ip, port = port, onMessage = { opposeState ->
                    this@FuTaRiViewModel.opposeState =
                        Json.decodeFromString<TwentyFourGameState>(opposeState)
                })
            } else if (right == GameRight.Client) {
                var bool = true
                while (bool){
                    try {
                        Thread.sleep(200)
                        serviceGame.clientStart(ip = ip, port = port, onMessage = { opposeState ->
                            this@FuTaRiViewModel.opposeState =
                                Json.decodeFromString<TwentyFourGameState>(opposeState)
                        })
                        Thread.sleep(2000)
                        bool = false
                        Log.d(tag, "init: run once $ip $port")
                    } catch (e: Exception) {
                        e.localizedMessage
                    }

                }
            }
        }
    }
}
