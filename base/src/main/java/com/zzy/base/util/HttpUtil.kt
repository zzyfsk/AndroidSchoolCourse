package com.zzy.base.util

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

    suspend inline fun <reified T> get(url: String, params: Map<String, String>): T? {
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
        return null
    }

    inline fun <reified T> post(url: String, params: Map<String, String>): T? {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val client = HttpClient(CIO)
                val response = client.post(url) {
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
        return null
    }

    companion object {
        val instance = HttpUtil()
    }
}