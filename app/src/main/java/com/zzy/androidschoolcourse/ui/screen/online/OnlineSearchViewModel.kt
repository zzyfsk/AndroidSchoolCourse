package com.zzy.androidschoolcourse.ui.screen.online

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.socket.service.ServiceFind
import com.zzy.androidschoolcourse.util.IPUtil

class OnlineSearchViewModel : ScreenModel {
    var showDialog by mutableStateOf(false)

    val deviceList = mutableStateListOf<String>()

    var ip: String = "0.0.0.0"
    private var run = false

    private val serviceFind = ServiceFind()

    fun start(context: Context,navigate:()->Unit){
        ip = IPUtil.ipUtil.getWifiIP(context)
        serviceFind.serverStart()
        serviceFind.controllerStart(ip)
    }

    fun find(){
        deviceList.clear()
        serviceFind.findServer(ip, onFind = {deviceList.add(it)})
        deviceList.add("0.0.0.0")
        deviceList.remove("0.0.0.0")
    }

    fun connect(){
        serviceFind.controllerStart(ip)
    }

    fun sendMessage(){
        serviceFind.clientSendMessage("test")
    }

    fun finish(){
        serviceFind.finish()
    }
}