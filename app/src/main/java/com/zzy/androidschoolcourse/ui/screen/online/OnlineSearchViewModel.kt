package com.zzy.androidschoolcourse.ui.screen.online

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.server.find.ClientFindNew
import com.zzy.androidschoolcourse.net.server.find.ServerFind
import com.zzy.androidschoolcourse.util.IPUtil

class OnlineSearchViewModel : ScreenModel {
    var showDialog by mutableStateOf(false)

    val deviceList = mutableStateListOf<String>()

    var ip: String = "0.0.0.0"
    private var run = false

    fun start(context: Context,navigate:()->Unit){
        ip = IPUtil.ipUtil.getWifiIP(context)
        ServerFind.runServer(ip = ip, port = 5124, onConnect = {
            showDialog = true
        }, onConnectSecond = {
            navigate()
        })
    }

    fun end(){
        clientShutDown()
    }

    fun clientStart(){
        deviceList.clear()
        val clientFind = ClientFindNew(ip = ip)
        clientFind.clientStart(onFind = {
            deviceList.add(it)
        })
    }

    fun clientConnect(target:String,port:Int=5124,navigate:()->Unit={}){
        val clientFind = ClientFindNew(ip = target,port = port)
        clientFind.clientConnect(onSuccess = {
            navigate()
        })
    }

    fun clientShutDown(){
        val clientFind = ClientFindNew(ip = ip)
        clientFind.clientShutDown()
    }
}