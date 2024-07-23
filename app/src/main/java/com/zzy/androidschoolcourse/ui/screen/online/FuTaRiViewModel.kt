package com.zzy.androidschoolcourse.ui.screen.online

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.QuestionService
import com.zzy.androidschoolcourse.net.socket.bean.BeanGameState
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.net.socket.game.ServiceGame
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class FuTaRiViewModel(
    private val ip: String,
    private val port: Int,
    private val right: GameRight,
    private val context: Context
) :
    ScreenModel {
    private val tag = "FuTaRiViewModel"
    private val serviceGame = ServiceGame()
    var opposeState by mutableStateOf(TwentyFourGameState())
    var gameState by mutableStateOf(TwentyFourGameState())


    fun sendGameState() {
        serviceGame.sendGameState(gameState, right)
    }

    private fun readInitNumber(context: Context): String {
        return QuestionService.questionService.readQuestion(
            context = context,
            fileName = "dataSet.txt"
        )
    }

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            if (right == GameRight.Command) {
                serviceGame.serverStart()
                serviceGame.controllerStart(ip = ip, port = port, onSet = { message ->
                    val state = Json.decodeFromString<BeanGameState>(message)
                    gameState = TwentyFourGameState(numbers = state.initNumber)
                }, onGet = {
                    return@controllerStart readInitNumber(context)
                }, onMessage = { opposeState ->
                    Log.d(tag, "init: $opposeState")
                    this@FuTaRiViewModel.opposeState =
                        Json.decodeFromString<TwentyFourGameState>(opposeState)
                })
            } else if (right == GameRight.Client) {
                var bool = true
                while (bool) {
                    try {
                        Thread.sleep(200)
                        serviceGame.clientStart(ip = ip, port = port, onSet = { message ->
                            Log.e("tag", "init: number changed", )
                            val state = Json.decodeFromString<BeanGameState>(message)
                            gameState = TwentyFourGameState(numbers = state.initNumber)
                        }, onMessage = { opposeState ->
                            this@FuTaRiViewModel.opposeState =
                                Json.decodeFromString<TwentyFourGameState>(opposeState)
                        })
                        bool = false
                    } catch (e: Exception) {
                        e.localizedMessage
                    }

                }
            }
        }
    }

    init {
        Log.d(tag, "ip:$ip port:$port right:$right ");
    }
}
