package com.zzy.androidschoolcourse.ui.screen.online

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.server.find.ClientFind
import com.zzy.androidschoolcourse.net.server.find.ServerFind
import com.zzy.androidschoolcourse.util.IPUtil

class OnlineSearchViewModel : ScreenModel {
    var showDialog by mutableStateOf(false)

    val deviceList = mutableStateListOf<String>()

    var ip: String = "0.0.0.0"
    private var run = false

    fun start(context: Context){
        ip = IPUtil.ipUtil.getWifiIP(context)
        val serverFind = ServerFind()
        serverFind.runServer(ip = ip, port = 5124, onConnect = {
            showDialog = true
        })
    }

    fun end(){

    }

    fun clientStart(){
        deviceList.clear()
        val clientFind = ClientFind()
        clientFind.clientStart(ip = ip,port = 5124, onFind = {
            deviceList.add(it)
        })
    }

    fun clientConnect(target:String,port:Int){
        val clientFind = ClientFind()
        clientFind.clientConnect(ip = target,port = port, onSuccess = {})
    }

    data class SearchResult(val name: String, val address: String)
}