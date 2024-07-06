package com.zzy.androidschoolcourse.net.server.game

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class ServerGame(port: Int = 5123) {

    private var serverSocket: ServerSocket? = null
    private val sockets = mutableMapOf<String, Socket>()
    private var socket:Socket? = null

    init {
        try {
            val address = InetAddress.getLocalHost()
            println(address)
            serverSocket = ServerSocket(port)
        } catch (e: IOException) {
            println(e.localizedMessage)
        }
    }

    fun startServer(){
        while (true){
            socket = serverSocket?.accept()
            socket?.localAddress?.hostAddress?.let { sockets.put(it, socket!!) }
            println(socket?.inetAddress)
            CoroutineScope(Dispatchers.IO).launch {
                serverStart()
            }
        }
    }

    private fun serverStart(){
        val num = sockets.size
        try {
            sockets.forEach{
                val dis = DataInputStream(it.value.getInputStream())
                val dos = DataOutputStream(it.value.getOutputStream())
                while (true){
                    val message = dis.readUTF()
                    println(message)
                    dos.writeUTF(message)
                    dos.flush()
                    if (sockets.size!=num) break
                }
            }
        }catch (e:IOException){
            println(e.localizedMessage)
        }
    }


}