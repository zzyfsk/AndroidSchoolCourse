package com.zzy.base.http.bean

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailHttp(
    val id: Long = 0,
    val name: String = "",
    val score: Int = 0,
    val signature: String = ""
)
