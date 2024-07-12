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

class ClientFind (private val ip: String, private val port: Int = 5124){
    private lateinit var socket : Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter


    fun start(){
        CoroutineScope(Dispatchers.IO).launch {
            socket = Socket(ip, port)
            input = BufferedReader(InputStreamReader(socket.getInputStream()))
            output = PrintWriter(socket.getOutputStream(), true)

            while (true){
                val message = input.readLine()
                if (message != null){
                    val msg = Json.decodeFromString<BeanSocketFind>(message)
                    when(msg.type){
                        SocketMessage.Function -> {

                        }
                        SocketMessage.Message -> {
                            Log.d("tag", "start: ${msg.content}")
                        }
                        SocketMessage.Exit -> {
                            socket.close()
                        }
                    }
                }

            }

        }
    }

    fun sendMessage(message:BeanSocketFind){
        CoroutineScope(Dispatchers.IO).launch {
            output.println(Json.encodeToString(message))
        }
    }


}