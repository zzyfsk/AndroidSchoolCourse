package com.zzy.androidschoolcourse.net.socket.bean

import kotlinx.serialization.Serializable

@Serializable
data class BeanSocketFind(val type:SocketMessage,val content:String)

enum class SocketMessage {
    Function,
    Message,
    Exit
}