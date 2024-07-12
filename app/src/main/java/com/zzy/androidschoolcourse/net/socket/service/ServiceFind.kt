package com.zzy.androidschoolcourse.net.socket.service

import com.zzy.androidschoolcourse.net.socket.bean.BeanSocketFind
import com.zzy.androidschoolcourse.net.socket.bean.SocketMessage
import com.zzy.androidschoolcourse.net.socket.find.ClientFind
import com.zzy.androidschoolcourse.net.socket.find.ServerFind

class ServiceFind {
    private lateinit var server:ServerFind
    private lateinit var controller:ClientFind
    private lateinit var client:ClientFind


    fun serverStart() {
        server = ServerFind()
        server.start()
    }

    fun controllerStart(ip:String){
        controller = ClientFind(ip)
        controller.start()
    }

    fun clientStart(ip: String){
        client = ClientFind(ip)
        client.start()
    }

    fun clientSendMessage(message:String){
        controller.sendMessage(BeanSocketFind(SocketMessage.Message,message))
    }

    fun finish(){
        controller.sendMessage(BeanSocketFind(SocketMessage.Exit,""))
    }


}