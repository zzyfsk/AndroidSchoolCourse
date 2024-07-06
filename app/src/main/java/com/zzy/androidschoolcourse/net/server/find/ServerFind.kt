package com.zzy.androidschoolcourse.net.server.find

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class ServerFind {

    fun runServer(ip: String, port: Int = 5124,onConnect:()->Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            val serverSocket = ServerSocket(port)


            while (true){
                val clientSocket = serverSocket.accept()
                val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val output = PrintWriter(clientSocket.getOutputStream(), true)

                var message: String?
                while (true) {
                    message = input.readLine()
                    if (message !=null)
                        Log.d("tag", "runServer: $message")
                    when (message) {
                        "find" -> {
                            output.println(ip)
                            clientSocket.close()
                            break
                        }

                        "connect" -> {
                            onConnect()
                            clientSocket.close()
                            break
                        }
                    }
                }
            }
        }
    }
}