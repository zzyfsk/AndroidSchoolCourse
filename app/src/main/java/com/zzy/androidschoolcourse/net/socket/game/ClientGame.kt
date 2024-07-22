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
import java.net.Socket
import java.net.SocketException

class ClientGame {
    private lateinit var socket: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter
    private var right = GameRight.Client

    fun start(
        ip:String,
        port:Int = 5123,
        onEvent:(GameSocketState,String)->Unit={ _,_-> }
    ){
        socket = Socket(ip,port)
        input = BufferedReader(InputStreamReader(socket.getInputStream()))
        output = PrintWriter(socket.getOutputStream(),true)

        CoroutineScope(Dispatchers.IO).launch{
            try {
                while (true){
                    val message = input.readLine()
                    if (message != null){
                        val msg = Json.decodeFromString<BeanSocketGame>(message)
                        when(msg.type){
                            GameSocketState.Function -> {

                            }
                            GameSocketState.Message -> {
                                onEvent(GameSocketState.Message,msg.content)
                            }
                            GameSocketState.Right -> {

                            }
                            GameSocketState.Exit -> {
                                sendMessage(BeanSocketGame(GameSocketState.Exit,"",GameRight.All))
                                socket.close()
                                onEvent(GameSocketState.Exit,"")
                            }
                        }
                    }
                }
            }catch (e:SocketException){
                e.localizedMessage
            }
        }
    }

    fun setRight(right:GameRight){
        this.right = right
        sendMessage(BeanSocketGame(GameSocketState.Right,"",right))
    }

    fun sendMessage(message:BeanSocketGame){
        CoroutineScope(Dispatchers.IO).launch{
            output.println(Json.encodeToString(message))
        }
    }

}