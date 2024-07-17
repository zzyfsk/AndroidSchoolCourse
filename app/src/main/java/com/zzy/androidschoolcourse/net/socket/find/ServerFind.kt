package com.zzy.androidschoolcourse.net.socket.find

import android.util.Log
import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketFind
import com.zzy.androidschoolcourse.net.socket.bean.SocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
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

class ServerFind {
    private val port = 5124

    val users = mutableListOf<FindTask>()
    private val right = mutableMapOf<FindTask, String>()
    private var run = true
    private var serverSocket: ServerSocket? = null
    val messageQueue = ArrayBlockingQueue<BeanSocketFind>(20)
    private var ip: String = ""


    fun start(ip: String = "0.0.0.0") {
        this@ServerFind.ip = ip
        serverSocket = ServerSocket(port)
        CoroutineScope(Dispatchers.IO).launch {
            PushMessageTask().run()
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                while (run) {
                    val socket = serverSocket?.accept()
                    CoroutineScope(Dispatchers.IO).launch {
                        val findTask = FindTask(socket!!, onFinish)
                        users.add(findTask)
                        findTask.start()
                    }
                }
            } catch (e: SocketException) {
                e.localizedMessage
            }
        }

    }

    private val onFinish: () -> Unit = {
        serverSocket?.close()
        run = false
    }

    inner class PushMessageTask {
        fun run() {
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    var message: BeanSocketFind? = null
                    try {
                        message = messageQueue.take()
                    } catch (e: InterruptedException) {
                        println(e.message)
                    }
                    if (message != null) {
                        Log.d("tag", "run: ${users.size}")
                        for (user in users) {
                            user.sendMessage(message)
                        }
                    }
                }
            }
        }
    }

    inner class FindTask(private val socket: Socket, val onFinish: () -> Unit) {
        private val input: BufferedReader =
            BufferedReader(InputStreamReader(socket.getInputStream()))
        private val output: PrintWriter = PrintWriter(socket.getOutputStream(), true)

        private val tag = "FindSocket"

        fun start() {
            CoroutineScope(Dispatchers.IO).launch {
                var message: String?
                while (true) {
                    try {
                        message = input.readLine()
                        if (message != null) {
                            val msg = Json.decodeFromString<BeanSocketFind>(message)
                            Log.d(tag, "right: ${right[this@FindTask]} msg: ${msg.content}")
                            when (msg.type) {
                                SocketMessage.Function -> {
                                    if (msg.content == "find") {
                                        this@FindTask.sendMessage(
                                            BeanSocketFind(
                                                SocketMessage.Function,
                                                ip
                                            )
                                        )
                                    }
                                    if (right[this@FindTask] == "command" && msg.content == "exit") {
                                        Log.d(tag, "start: exit")
                                        messageQueue.put(BeanSocketFind(SocketMessage.Exit, "exit"))
                                        socket.close()
                                    }
                                    if (msg.content == "command") {
                                        right[this@FindTask] = msg.content
                                    }
                                    if (msg.content == "client") {
                                        right[this@FindTask] = msg.content
                                    }
                                    if (msg.content == "connect") {
                                        messageQueue.put(
                                            BeanSocketFind(
                                                SocketMessage.Function,
                                                "connect"
                                            )
                                        )
                                    }
                                }

                                SocketMessage.Message -> {
                                    Log.d(tag, "start: ${msg.content}")
                                    messageQueue.put(
                                        BeanSocketFind(
                                            SocketMessage.Message,
                                            msg.content
                                        )
                                    )
                                }

                                SocketMessage.Exit -> {
                                    if (right[this@FindTask] == "command") {
                                        messageQueue.put(BeanSocketFind(SocketMessage.Exit, "exit"))
                                        socket.close()
                                        onFinish()
                                    }
                                }

                                SocketMessage.Result -> {
                                    messageQueue.put(BeanSocketFind(SocketMessage.Result,msg.content))
                                }
                            }
                        }
                    } catch (e: SocketException) {
                        e.localizedMessage
                    }
                }
            }
        }

        fun sendMessage(message: BeanSocketFind) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    output.println(Json.encodeToString(message))
                } catch (e: Exception) {
                    e.localizedMessage
                }
            }
        }

        init {
            Log.d(tag, "new user")
        }

    }

}

