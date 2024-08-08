package com.zzy.androidschoolcourse.net.socket.game

import android.util.Log
import com.zzy.androidschoolcourse.net.socket.bean.BeanGameState
import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketGame
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.net.socket.bean.GameSocketState
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ServiceGame {
private lateinit var server: ServerGame
    private lateinit var controller: ClientGame
    private lateinit var client: ClientGame

    fun serverStart() {
        server = ServerGame()
        CoroutineScope(Dispatchers.IO).launch {
            server.start()
        }
    }

    fun controllerStart(
        ip: String,
        port: Int,
        onSet: (String) -> Unit = {},
        onGet: () -> String,
        onMessage: (String) -> Unit,
        onWin: () -> Unit = {},
        getChat: (String) -> Unit = {}
    ) {
        controller = ClientGame(ip, port)
        val right = GameRight.Command
        controller.start { type, message ->
            Log.e("tag", "controllerStart: $type $message")
            when (type) {
                GameSocketState.Function -> {
                    if (message == "init") {
                        controller.sendMessage(
                            BeanSocketGame(
                                GameSocketState.Set,
                                Json.encodeToString(BeanGameState(initNumber = onGet())),
                                right
                            )
                        )
                    }
                    if (message == "Win") {
                        onWin()
                    }
                }

                GameSocketState.Message -> {
                    onMessage(message)
                }

                GameSocketState.Right -> TODO()
                GameSocketState.Exit -> {}
                GameSocketState.Set -> {
                    onSet(message)

                }

                GameSocketState.Chat -> TODO()
            }
        }
        controller.setRight(GameRight.Command)
    }

    fun clientStart(
        ip: String,
        port: Int,
        onSet: (String) -> Unit = {},
        onMessage: (String) -> Unit,
        onWin: () -> Unit = {},
        getChat: (String) -> Unit = {}
    ) {
        client = ClientGame(ip, port)
        client.start { type, message ->
            when (type) {
                GameSocketState.Function -> {
                    if (message == "Win") {
                        onWin()
                    }
                }

                GameSocketState.Message -> {
                    onMessage(message)
                }

                GameSocketState.Right -> TODO()
                GameSocketState.Exit -> TODO()
                GameSocketState.Set -> {
                    onSet(message)
                }

                GameSocketState.Chat -> TODO()
            }
        }
        client.setRight(GameRight.Client)
        clientSendGameStart()
    }

    private fun clientSendGameStart() {
        client.sendMessage(
            BeanSocketGame(
                GameSocketState.Function,
                "Start",
                GameRight.Client
            )
        )
    }

    fun sendGameState(gameState: TwentyFourGameState, right: GameRight) {
        println(Json.encodeToString(gameState))
        if (right == GameRight.Client) {
            client.sendMessage(
                BeanSocketGame(
                    GameSocketState.Message,
                    Json.encodeToString(gameState),
                    GameRight.Client
                )
            )
        } else if (right == GameRight.Command) {
            controller.sendMessage(
                BeanSocketGame(
                    GameSocketState.Message,
                    Json.encodeToString(gameState),
                    GameRight.Command
                )
            )
        }
    }

    fun sendWin(right: GameRight) {
        if (right == GameRight.Client) {
            Log.d("tag", "sendGameState: client")
            client.sendMessage(
                BeanSocketGame(
                    GameSocketState.Function,
                    "Win",
                    GameRight.Client
                )
            )
        } else if (right == GameRight.Command) {
            controller.sendMessage(
                BeanSocketGame(
                    GameSocketState.Function,
                    "Win",
                    GameRight.Command
                )
            )
        }
    }

    fun clientChat(message: String) {
        client.sendMessage(
            BeanSocketGame(
                GameSocketState.Chat,
                message,
                GameRight.Client
            )
        )
    }

    fun controllerSendChat(message: String) {
        controller.sendMessage(
            BeanSocketGame(
                GameSocketState.Chat,
                message,
                GameRight.Command
            )
        )
    }

    companion object {
        val service = ServiceGame()
    }
}