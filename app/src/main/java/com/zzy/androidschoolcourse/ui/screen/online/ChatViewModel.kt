package com.zzy.androidschoolcourse.ui.screen.online

import kotlinx.serialization.Serializable

@Serializable
data class ChatContent(val chatRole: ChatRole,val name:String = "",val content:String)

@Serializable
enum class ChatRole{
    Me,
    Oppose
}