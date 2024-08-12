package com.zzy.base.util

import com.zzy.base.http.bean.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class HttpUtil {

    suspend inline fun <reified T> get(url: String, params: Map<String, String>): Result<T> {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val client = HttpClient(CIO)
                val response = client.get(url) {
                    params.forEach {
                        parameter(it.key, it.value)
                    }
                }
                client.close()
                return@launch Json.decodeFromString(response.bodyAsText())
            } catch (e: Exception) {
                e.localizedMessage
            }
        }
        return Result.error("-1", "请求失败")
    }

    suspend inline fun <reified T> post(url: String, params: Map<String, String>): Result<T> {
        try {
            val client = HttpClient(CIO)
            val response = client.post(url) {
                params.forEach {
                    parameter(it.key, it.value)
                }
            }
            client.close()
            println(response.bodyAsText())
            return Json.decodeFromString(response.bodyAsText())
        } catch (e: Exception) {
//            e.localizedMessage
            println(e.message)
            return Result.error("-1", "请求失败")
        }
    }

    companion object {
        val instance = HttpUtil()
    }
}