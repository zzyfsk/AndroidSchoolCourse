package com.zzy.androidschoolcourse.net.socket.bean

import kotlinx.serialization.Serializable

@Serializable
data class BeanSocketGame(val type:GameSocketState,val content:String, val right: GameRight)

@Serializable
data class BeanGameState(val initNumber:String)

enum class GameSocketState{
    Function,
    Message,  // ゲーム　メッセージ
    Right,
    Set,
    Chat,
    Exit
}

enum class GameRight {
    Command,
    Client,
    All
}