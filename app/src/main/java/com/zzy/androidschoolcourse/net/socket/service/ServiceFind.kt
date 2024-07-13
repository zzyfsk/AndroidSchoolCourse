package com.zzy.androidschoolcourse.net.socket.service

import android.util.Log
import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketFind
import com.zzy.androidschoolcourse.net.socket.bean.SocketMessage
import com.zzy.androidschoolcourse.net.socket.find.ClientFind
import com.zzy.androidschoolcourse.net.socket.find.ServerFind
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException

class ServiceFind {
    private lateinit var server: ServerFind
    private lateinit var controller: ClientFind
    private lateinit var client: ClientFind

    fun serverStart(ip: String,onConnect:()->Unit={}) {
        server = ServerFind()
        server.start(ip)
    }

    fun controllerStart(ip: String) {
        CoroutineScope(Dispatchers.IO).launch {
            controller = ClientFind()
            controller.start(ip)
            controller.setRight("command")
        }
    }

    fun clientConnect(ip: String,onConnect: () -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            client = ClientFind()
            client.start(ip, onConnect = onConnect)
            client.sendMessage(BeanSocketFind(SocketMessage.Function,"connect"))
        }
    }

    fun clientStart(ip: String) {
        client = ClientFind()
        client.start(ip)
        controller.sendMessage(BeanSocketFind(SocketMessage.Function,"client"))
    }

    fun controllerSendMessage(message: String) {
        controller.sendMessage(BeanSocketFind(SocketMessage.Message, message))
    }

    fun controllerSendResult(boolean: Boolean){
        controller.sendMessage(BeanSocketFind(SocketMessage.Result,if (boolean)"true" else "false"))
    }

    fun finish() {
        controller.sendMessage(BeanSocketFind(SocketMessage.Exit, ""))
    }

    fun findServer(ip: String, onFind: (String) -> Unit = {}) {
        val realIP = ip.substring(0, ip.lastIndexOf(".") + 1)
        for (i in 101..101) {
            Log.e("tag", realIP + i.toString())
            CoroutineScope(Dispatchers.IO).launch {
                client = ClientFind()
                try {
                    client.start(
                        ip = realIP + i.toString(),
                        onFind = onFind
                    )
                } catch (e: ConnectException) {
                    println(e.message)
                } catch (e: NoRouteToHostException) {
                    println(e.message)
                }
                Log.d("tag", "start: true")
                client.sendMessage(BeanSocketFind(SocketMessage.Function, "find"))
            }
        }
    }
}