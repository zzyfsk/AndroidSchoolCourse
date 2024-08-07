package com.zzy.androidschoolcourse.ui.screen.online

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.socket.find.ServiceFind
import com.zzy.androidschoolcourse.util.IPUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OnlineSearchViewModel : ScreenModel {
    var showDialog by mutableStateOf(false)

    val deviceList = mutableStateListOf<String>()

    var ip: String = "0.0.0.0"

    private val serviceFind = ServiceFind()

    fun start(context: Context) {
        ip = IPUtil.ipUtil.getWifiIP(context)
        serviceFind.serverStart(ip)
        serviceFind.controllerStart(ip, onConnect = {
            showDialog = true
        })
    }

    fun find() {
        deviceList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            serviceFind.findDevices(ip)
                .onEach {
                    deviceList.add(it)
                    Log.e("tag", "fun find: $it")
                }
                .collect {}
        }
    }

    fun connect(serverIP: String,onConfirm:()->Unit) {
        serviceFind.clientConnect(serverIP, onConnect = { showDialog = true }, onConfirm = {
            serviceFind.finish()
            onConfirm()
        }, onDisConfirm = {})
    }

    fun sendResult(boolean: Boolean) {
        serviceFind.controllerSendResult(boolean)
    }

    fun sendMessage() {
        serviceFind.controllerSendMessage("test")
    }

    fun finish() {
        serviceFind.finish()
    }
}