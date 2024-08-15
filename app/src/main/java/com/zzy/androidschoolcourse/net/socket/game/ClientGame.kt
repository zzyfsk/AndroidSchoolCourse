package com.zzy.androidschoolcourse.net.socket.game

import android.util.Log
import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketGame
import com.zzy.androidschoolcourse.net.socket.bean.GameRight
import com.zzy.androidschoolcourse.net.socket.bean.GameSocketState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.net.SocketException

class ClientGame(
    ip: String,
    port: Int = 5123,
) {
    private var socket: Socket = Socket(ip, port)
    private var input: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
    private var output: PrintWriter = PrintWriter(socket.getOutputStream(), true)
    private var right = GameRight.Client
    private var socketState = SocketState.Uninitialized

    fun start(
        onEvent: (GameSocketState, String) -> Unit = { _, _ -> }
    ) {
        socketState = SocketState.Start
        CoroutineScope(Dispatchers.IO).launch {
            try {
                while (true) {
                    val message = input.readLine()
                    if (message != null) {
                        val msg = Json.decodeFromString<BeanSocketGame>(message)
                        when (msg.type) {
                            GameSocketState.Function -> {
                                if (msg.content == "Start") {
                                    onEvent(GameSocketState.Function, "init")
                                }
                                if (msg.content == "Win") {
                                    onEvent(GameSocketState.Function, "Win")
                                }
                            }

                            GameSocketState.Message -> {
                                onEvent(GameSocketState.Message, msg.content)
                            }

                            GameSocketState.Right -> {

                            }

                            GameSocketState.Set -> {
                                onEvent(GameSocketState.Set, msg.content)
                            }

                            GameSocketState.Exit -> {
                                sendMessage(BeanSocketGame(GameSocketState.Exit, "", GameRight.All))
                                socket.close()
                                onEvent(GameSocketState.Exit, "")
                            }

                            GameSocketState.Chat -> {
                                onEvent(GameSocketState.Chat,msg.content)
                            }
                        }
                    }
                }
            } catch (e: SocketException) {
                e.localizedMessage
            }
        }
    }

    fun setRight(right: GameRight) {
        this.right = right
        sendMessage(BeanSocketGame(GameSocketState.Right, "", right))
    }

    fun sendMessage(message: BeanSocketGame) {
        Log.d("gameSocket", """
            type:${message.type}
            content:${message.content}
            right:${message.right}
        """.trimIndent())
        CoroutineScope(Dispatchers.IO).launch {
            output.println(Json.encodeToString(message))
        }
    }


    enum class SocketState {
        Uninitialized,
        Start
    }
}