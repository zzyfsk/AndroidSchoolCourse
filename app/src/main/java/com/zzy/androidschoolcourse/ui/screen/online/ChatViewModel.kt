package com.zzy.androidschoolcourse.ui.screen.online

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.serialization.Serializable

class ChatViewModel:ScreenModel {
    val chatList = mutableListOf<ChatContent>()
}

@Serializable
data class ChatContent(val chatRole: ChatRole,val name:String = "",val content:String)

@Serializable
enum class ChatRole{
    Me,
    Oppose
}