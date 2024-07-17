package com.zzy.androidschoolcourse.net.socket.find

import android.util.Log
import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketFind
import com.zzy.androidschoolcourse.net.socket.bean.SocketMessage
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

class ClientFind {
    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter
    private var right = "client"

    fun start(
        ip: String,
        port: Int = 5124,
        onFind: (String) -> Unit = {},
        onConnect: () -> Unit = {},
        onConfirm: () -> Unit = {},
        onDisConFirm: () -> Unit = {}
    ) {
        socket = Socket(ip, port)
        input = BufferedReader(InputStreamReader(socket.getInputStream()))
        output = PrintWriter(socket.getOutputStream(), true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                while (true) {
                    val message = input.readLine()
                    if (message != null) {
                        val msg = Json.decodeFromString<BeanSocketFind>(message)
                        Log.e("tag", "client $message , $right")
                        when (msg.type) {
                            SocketMessage.Function -> {
                                if (msg.content.contains(".")) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Log.e("tag", "collect the msg", )
                                        onFind(msg.content)
                                    }
                                    sendMessage(BeanSocketFind(SocketMessage.Function, "exit"))
                                    socket.close()
                                    Log.e("tag", "find: $ip")
                                }
                                if (msg.content == "connect" && right == "command") {
                                    onConnect()
                                }
                            }

                            SocketMessage.Message -> {
                                Log.d("tag", "start: ${msg.content}")
                            }

                            SocketMessage.Exit -> {
                                socket.close()
                            }

                            SocketMessage.Result -> {
                                if (msg.content == "true") {
                                    onConfirm()
                                }
                                if (msg.content == "false") {
                                    onDisConFirm()
                                }
                            }
                        }
                    }
                }
            } catch (e: SocketException) {
                e.localizedMessage
            }
        }
    }

    fun setRight(right: String) {
        this.right = right
        sendMessage(BeanSocketFind(SocketMessage.Function, right))
    }

    fun sendMessage(message: BeanSocketFind) {
        CoroutineScope(Dispatchers.IO).launch {
            output.println(Json.encodeToString(message))
        }
    }


}