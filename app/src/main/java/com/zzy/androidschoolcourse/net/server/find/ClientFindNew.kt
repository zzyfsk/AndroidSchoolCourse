package com.zzy.androidschoolcourse.net.server.find

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.Socket

class ClientFindNew(private val ip: String, private val port: Int = 5124) {

    fun clientStart(onFind: (String) -> Unit) {
        for (i in 1..255) {
            val findIP = ip.substring(0, ip.lastIndexOf(".") + 1) + i.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val socket = Socket(findIP, port)
                    val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                    val output = PrintWriter(socket.getOutputStream(), true)
                    output.println("find")
                    var message: String?
                    while (true) {
                        message = input.readLine()
                        if (message != null) {
                            onFind(message)
                            break
                        }
                    }
                    socket.close()
                } catch (_: ConnectException) {
                } catch (_: NoRouteToHostException) {
                }
            }
        }
    }

    fun clientConnect(onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket(ip, port)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output = PrintWriter(socket.getOutputStream(), true)
                output.println("connect")
                var message: String?
                while (true) {
                    message = input.readLine()
                    if (message == "yes") {
                        onSuccess()
                        output.println("exit")
                        break
                    }
                }
                socket.close()
            } catch (_: ConnectException) {
            } catch (_: NoRouteToHostException) {
            }
        }
    }

    fun clientShutDown() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket(ip, port)
                BufferedReader(InputStreamReader(socket.getInputStream()))
                val output = PrintWriter(socket.getOutputStream(), true)
                output.println("exit")
                socket.close()
            } catch (_: ConnectException) {
            } catch (_: NoRouteToHostException) {
            }
        }
    }

}