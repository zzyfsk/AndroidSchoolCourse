package com.zzy.androidschoolcourse.net.server.find

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

object ServerFind {
    private var serverSocket:ServerSocket? = null
    private var finish = false
    private var connect = false
    
    private val tag = "findServer"
//    private var sendMessage = ""


    fun runServer(ip: String, port: Int = 5124,onConnect:()->Unit,onConnectSecond: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            serverSocket = ServerSocket(port)

            while (!finish){
                val clientSocket = serverSocket!!.accept()
                val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val output = PrintWriter(clientSocket.getOutputStream(), true)

                var message: String?
                while (!finish) {
                    message = input.readLine()
                    if (message !=null)
                        Log.d("tag", "runServer: $message")
                    when (message) {
                        "find" -> {
                            output.println(ip)
                            break
                        }
                        "connect" -> {
                            onConnect()
                        }
                        "exit"->{
                            finish = true
                            break
                        }
                    }
                    if (connect){
                        onConnectSecond()
                        output.println("yes")
                        connect = false
                        finish = true
                        break
                    }
                }
                clientSocket.close()
            }
            Log.d(tag, "runServer: server shutdown")
            serverSocket?.close()
            serverSocket = null
        }
    }

    fun connect(){
        connect = true
    }

    fun stopServer(){
        finish = true
    }
}