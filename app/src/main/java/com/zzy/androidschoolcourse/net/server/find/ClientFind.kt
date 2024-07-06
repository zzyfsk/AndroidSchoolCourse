package com.zzy.androidschoolcourse.net.server.find

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.Socket

class ClientFind {

    fun clientStart(ip: String, port: Int = 5124, onFind: (String) -> Unit) {
        for (i in 1..255) {
            val findIP = ip.substring(0, ip.lastIndexOf(".") + 1) + i.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val socket = Socket(findIP, port)
                    val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                    val output = PrintWriter(socket.getOutputStream(), true)
                    Log.d("tag", "clientStart: success")
                    output.println("find")
                    val message: String?
                    while (true) {
                        message = input.readLine()
                        if (message != null) {
                            onFind(message)
                        }
                        break
                    }
                    socket.close()
                } catch (e: ConnectException) {
                    Log.d("tag", "clientStart: $findIP connect failed")
                } catch (e: NoRouteToHostException) {
                    Log.e("tag", "clientStart: $findIP connect failed")
                    println(e.localizedMessage)
                }
            }
        }
    }

    fun clientConnect(ip: String, port: Int = 5124, onSuccess: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket(ip, port)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output = PrintWriter(socket.getOutputStream(), true)
                Log.d("tag", "clientStart: success")
                output.println("connect")
                val message: String?
                while (true) {
                    message = input.readLine()
                    if (message != null) {
                        onSuccess(message)
                    }
                    break
                }
                socket.close()
            } catch (e: ConnectException) {
                Log.d("tag", "clientStart: $ip connect failed")
            } catch (e: NoRouteToHostException) {
                Log.e("tag", "clientStart: $ip connect failed")
                println(e.localizedMessage)
            }
        }
    }

}