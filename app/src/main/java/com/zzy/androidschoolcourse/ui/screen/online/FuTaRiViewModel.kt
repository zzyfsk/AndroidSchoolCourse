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
import com.zzy.androidschoolcourse.ui.component.GameMode
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameRecord
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameState
import com.zzy.base.util.FileUtil
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
    private val serviceGame = ServiceGame.service
    var opposeState by mutableStateOf(TwentyFourGameState(numbers = "1234"))
    var gameState by mutableStateOf(TwentyFourGameState(numbers = "4321"))
    var showWin by mutableStateOf(false)
    private var winState by mutableStateOf(false)

    private val fileUtil = FileUtil(context)
    private val record = TwentyFourGameRecord(gameMode = GameMode.HiToRi)

    /**
     * @param recordPosition 1 or 2 自己或对面
     */
    fun recordState(recordPosition: Int, gameState: TwentyFourGameState) {
        record.record(recordPosition, gameState)
    }

    private fun saveState() {
        record.save(fileUtil = fileUtil)
    }

    fun sendGameState() {
        serviceGame.sendGameState(gameState, right)
    }

    private fun set() {
        opposeState.numbers[0].digitToInt().let { num ->
            opposeState.numberStateList[0].fraction.numerator = if (num == 0) 10 else num
        }
        opposeState.numbers[1].digitToInt().let { num ->
            opposeState.numberStateList[1].fraction.numerator = if (num == 0) 10 else num
        }
        opposeState.numbers[2].digitToInt().let { num ->
            opposeState.numberStateList[2].fraction.numerator = if (num == 0) 10 else num
        }
        opposeState.numbers[3].digitToInt().let { num ->
            opposeState.numberStateList[3].fraction.numerator = if (num == 0) 10 else num
        }
        showWin = false
        winState = false
    }

    private fun readInitNumber(context: Context): String {
        return QuestionService.questionService.readQuestion(
            context = context,
            fileName = "dataSet.txt"
        )
    }

    fun gameWin() {
        winState = true
        serviceGame.sendWin(right)
    }

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            if (right == GameRight.Command) {
                serviceGame.serverStart()
                serviceGame.controllerStart(ip = ip, port = port, onSet = { message ->
                    val state = Json.decodeFromString<BeanGameState>(message)
                    gameState = TwentyFourGameState(numbers = state.initNumber)
                    opposeState = TwentyFourGameState(numbers = state.initNumber)
                    set()
                }, onGet = {
                    return@controllerStart readInitNumber(context)
                }, onMessage = { opposeState ->
                    this@FuTaRiViewModel.opposeState =
                        Json.decodeFromString<TwentyFourGameState>(opposeState)
                    recordState(2, this@FuTaRiViewModel.opposeState)
                },
                    onWin = {
                        showWin = true
                        saveState()

                    })
            } else if (right == GameRight.Client) {
                var bool = true
                while (bool) {
                    try {
                        Thread.sleep(200)
                        serviceGame.clientStart(ip = ip, port = port, onSet = { message ->
                            val state = Json.decodeFromString<BeanGameState>(message)
                            gameState = TwentyFourGameState(numbers = state.initNumber)
                            opposeState = TwentyFourGameState(numbers = state.initNumber)
                            set()
                        }, onMessage = { opposeState ->
                            this@FuTaRiViewModel.opposeState =
                                Json.decodeFromString<TwentyFourGameState>(opposeState)
                        }, onWin = {
                            showWin = true
                            saveState()
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
        Log.d(tag, "ip:$ip port:$port right:$right ")
    }
}
