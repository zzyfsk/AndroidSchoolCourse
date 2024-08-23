package com.zzy.base.http.bean

import kotlinx.serialization.Serializable

@Serializable
data class UserHttp(
    var username: String = "",
    var id: Long,
    var token: String
)
