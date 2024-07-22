package com.zzy.androidschoolcourse.net.socket.find

import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketFind
import com.zzy.androidschoolcourse.net.socket.bean.SocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException

class ServiceFind {
    private lateinit var server: ServerFind
    private lateinit var controller: ClientFind
    private lateinit var client: ClientFind

    fun serverStart(ip: String) {
        server = ServerFind()
        server.start(ip)
    }

    fun controllerStart(ip: String, onConnect: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            controller = ClientFind()
            controller.start(ip, onConnect = onConnect)
            controller.setRight("command")
        }
    }

    fun clientConnect(
        ip: String,
        onConnect: () -> Unit,
        onConfirm: () -> Unit,
        onDisConfirm: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            client = ClientFind()
            client.start(
                ip,
                onConnect = onConnect,
                onConfirm = onConfirm,
                onDisConFirm = onDisConfirm
            )
            client.sendMessage(BeanSocketFind(SocketMessage.Function, "connect"))
        }
    }

//    fun clientStart(ip: String) {
//        client = ClientFind()
//        client.start(ip)
//        controller.sendMessage(BeanSocketFind(SocketMessage.Function, "client"))
//    }

    fun controllerSendMessage(message: String) {
        controller.sendMessage(BeanSocketFind(SocketMessage.Message, message))
    }

    fun controllerSendResult(boolean: Boolean) {
        controller.sendMessage(
            BeanSocketFind(
                SocketMessage.Result,
                if (boolean) "true" else "false"
            )
        )
    }

    fun finish() {
        controller.sendMessage(BeanSocketFind(SocketMessage.Exit, ""))
    }

//    fun findServer(ip: String, onFind: (String) -> Unit = {}) {
//        val realIP = ip.substring(0, ip.lastIndexOf(".") + 1)
//        for (i in 1..255) {
//            CoroutineScope(Dispatchers.IO).launch {
//                val clientFind = ClientFind()
//                try {
//                    clientFind.start(
//                        ip = realIP + i.toString(),
//                        onFind = onFind
//                    )
//                    Log.d("tag", "start: true")
//                    clientFind.sendMessage(BeanSocketFind(SocketMessage.Function, "find"))
//                } catch (e: ConnectException) {
//                    e.localizedMessage
//                } catch (e: NoRouteToHostException) {
//                    e.localizedMessage
//                }
//            }
//        }
//    }

    fun findDevices(ip: String): Flow<String> {
        val realIP = ip.substring(0, ip.lastIndexOf(".") + 1)
        var count = 0
        return callbackFlow {
            for (i in 1..255) {
                CoroutineScope(Dispatchers.IO).launch {
                    val clientFind = ClientFind()
                    try {
                        clientFind.start(
                            ip = realIP + i.toString(),
                            onFind = {
                                CoroutineScope(Dispatchers.IO).launch {
                                }
                            }
                        )
                        clientFind.sendMessage(BeanSocketFind(SocketMessage.Function, "find"))
                        send(realIP + i)
                    } catch (e: ConnectException) {
                        e.localizedMessage
                    } catch (e: NoRouteToHostException) {
                        e.localizedMessage
                    }
                }
                count++
            }
            Thread.sleep(5000)
            awaitClose{}
        }
            .flowOn(Dispatchers.IO)
    }
}