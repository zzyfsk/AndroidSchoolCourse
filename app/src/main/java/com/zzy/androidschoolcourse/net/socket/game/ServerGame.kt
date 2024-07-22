package com.zzy.androidschoolcourse.net.socket.game

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
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.ArrayBlockingQueue

class ServerGame {
    private val port = 5123

    private val right = mutableMapOf<GameTask, GameRight>()
    val messageQueue = ArrayBlockingQueue<BeanSocketGame>(20)
    val users = mutableListOf<GameTask>()
    private var run = true

    private var serverSocket: ServerSocket? = null

    fun start() {
        serverSocket = ServerSocket(port)
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(200)
            PushMessageTask().run()
        }
        CoroutineScope(Dispatchers.IO).launch {
            while (run) {
                val socket = serverSocket?.accept()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val gameTask = GameTask(socket!!)
                        users.add(gameTask)
                        gameTask.start()
                    } catch (e: SocketException) {
                        e.localizedMessage
                    }
                }
            }
        }
    }

    val finish: () -> Unit = {
        serverSocket?.close()
        run = false
    }

    inner class PushMessageTask {
        fun run() {
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    var message: BeanSocketGame? = null
                    try {
                        message = messageQueue.take()
                    } catch (e: InterruptedException) {
                        println(e.message)
                    }
                    if (message != null) {
                        for (user in users) {
                            if (message.right == GameRight.All || right[user] == message.right) {
                                user.sendMessage(message)
                            }
                        }
                    }
                }
            }
        }
    }

    inner class GameTask(
        private val socket: Socket
    ) {
        private val input: BufferedReader =
            BufferedReader(InputStreamReader(socket.getInputStream()))
        private val output: PrintWriter = PrintWriter(socket.getOutputStream(), true)

        fun start() {
            right[this@GameTask] = GameRight.Client
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    try {
                        val message = input.readLine()
                        if (message != null) {
                            val msg = Json.decodeFromString<BeanSocketGame>(message)
                            when (msg.type) {
                                GameSocketState.Function -> {

                                }

                                GameSocketState.Message -> {
                                    messageQueue.put(
                                        BeanSocketGame(
                                            GameSocketState.Message,
                                            msg.content,
                                            if (msg.right == GameRight.Client) GameRight.Command else GameRight.Client
                                        )
                                    )
                                }

                                GameSocketState.Right -> {
                                    right[this@GameTask] = msg.right
                                }

                                GameSocketState.Exit -> {
                                    if (GameRight.Command == right[this@GameTask]) {
                                        messageQueue.put(
                                            BeanSocketGame(
                                                GameSocketState.Exit,
                                                "exit",
                                                GameRight.All
                                            )
                                        )
                                        while (messageQueue.isNotEmpty()) Thread.sleep(100)
                                        socket.close()
                                        finish
                                    }
                                }
                            }
                        }
                    } catch (e: SocketException) {
                        e.localizedMessage
                    } finally {
                        right.remove(this@GameTask)
                    }
                }
            }
        }

        val sendMessage: (BeanSocketGame) -> Unit = { message ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    output.println(Json.encodeToString(message))
                } catch (e: Exception) {
                    e.localizedMessage
                }
            }
        }
    }
}