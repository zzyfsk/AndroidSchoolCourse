package com.zzy.base.http.bean

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailHttp(
    val id: Long = 0,
    val name: String = "",
    val score: Int = 0,
    val signature: String = "",
    val token: String = ""
){
    companion object{
        val testUser = UserDetailHttp(
            id = 1,
            name = "zzy_dada",
            score = 1000,
            signature = "这是一个测试用户"
        )
    }
}
