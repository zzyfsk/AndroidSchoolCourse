package com.zzy.androidschoolcourse.net.bean

import kotlinx.serialization.Serializable


@Serializable
data class GameModel(val state: GameState, val message: String)

enum class GameState{
    Play,
    Chat,
    Text
}
