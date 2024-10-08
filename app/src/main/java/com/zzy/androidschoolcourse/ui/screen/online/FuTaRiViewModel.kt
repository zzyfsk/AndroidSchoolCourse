package com.zzy.androidschoolcourse.ui.screen.online

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FuTaRiViewModel(
    private val ip: String,
    private val port: Int,
    private val right: GameRight,
    private val name: String,
    private val context: Context
) :
    ScreenModel {
    private val serviceGame = ServiceGame.service

    var opposeState by mutableStateOf(TwentyFourGameState(numbers = "1234"))
    var gameState by mutableStateOf(TwentyFourGameState(numbers = "4321"))
    var showWin by mutableStateOf(false)
    var showChat by mutableStateOf(false)
    private var winState by mutableStateOf(false)
    var chatContent by mutableStateOf("")
    val chatList = mutableStateListOf<ChatContent>()
    var huTaRiState by mutableStateOf(FuTaRiState.Socket)

    private val fileUtil = FileUtil(context)
    private val record = TwentyFourGameRecord(gameMode = GameMode.HuTaRi)

    /**
     * @param recordPosition 1 or 2 自己或对面
     */
    fun recordState(recordPosition: Int, gameState: TwentyFourGameState) {
        record.record(recordPosition, gameState)
    }

    private fun saveState() {
        record.recordChats(chatList)
        record.save(fileUtil = fileUtil)
    }

    fun sendGameState(gameState: TwentyFourGameState) {
        println(Json.encodeToString(gameState))
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
        opposeState = opposeState.copy()
        showWin = false
        winState = false

        initFinish()
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

    private fun onWin() {
        showWin = true
        saveState()
    }

    fun init() {
        serviceGame.serverStart()
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(100)
            if (right == GameRight.Command) {
                serviceGame.controllerStart(ip = ip, port = port,
                    onSet = { message ->
                        val state = Json.decodeFromString<BeanGameState>(message)
                        gameState = TwentyFourGameState(numbers = state.initNumber)
                        opposeState = TwentyFourGameState(numbers = state.initNumber)
                        set()
                    },
                    onGet = {
                        return@controllerStart readInitNumber(context)
                    },
                    onMessage = { opposeState ->
                        this@FuTaRiViewModel.opposeState =
                            Json.decodeFromString<TwentyFourGameState>(opposeState)
                        println(opposeState)
                        recordState(2, Json.decodeFromString(opposeState))
                    },
                    onWin = {
                        onWin()
                    },
                    getChat = { chatContent->
                        getChat(chatContent)
                    })
            } else if (right == GameRight.Client) {
                var bool = true
                while (bool) {
                    try {
                        Thread.sleep(200)
                        println("sleep:200")
                        serviceGame.clientStart(ip = ip, port = port,
                            onSet = { message ->
                                val state = Json.decodeFromString<BeanGameState>(message)
                                gameState = TwentyFourGameState(numbers = state.initNumber)
                                opposeState = TwentyFourGameState(numbers = state.initNumber)
                                set()
                            },
                            onMessage = { opposeState ->
                                this@FuTaRiViewModel.opposeState =
                                    Json.decodeFromString<TwentyFourGameState>(opposeState)
                                recordState(2, Json.decodeFromString(opposeState))
                            },
                            onWin = {
                                onWin()
                            })
                        bool = false
                    } catch (e: Exception) {
                        e.localizedMessage
                    }

                }
            }
        }
    }

    fun sendChat() {
        val chat = Json.encodeToString(
            ChatContent(
                chatRole = ChatRole.Oppose,
                name = name,
                content = chatContent
            )
        )
        chatList.add(ChatContent(chatRole = ChatRole.Me, name = name, content = chatContent))
        chatContent = ""
        when (right) {
            GameRight.Command -> {
                serviceGame.controllerSendChat(chat)
            }

            GameRight.Client -> {
                serviceGame.clientChat(chat)
            }

            GameRight.All -> TODO()
        }
    }

    private fun initFinish(){
        huTaRiState = FuTaRiState.UI
    }

    private fun getChat(chatContent: ChatContent){
        chatList.add(chatContent)
    }

    fun finish() {
        if (right == GameRight.Command) serviceGame.finish()
    }
}

enum class FuTaRiState {
    Socket,
    Data,
    UI
}
