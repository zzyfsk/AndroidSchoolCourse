package com.zzy.base.http.bean

import kotlinx.serialization.Serializable

@Serializable
class Result<T> {
    var code: String? = null
    var msg: String? = null
    private var data: T? = null

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }

    constructor()

    constructor(data: T) {
        this.data = data
    }

    companion object {
        fun success(): Result<*> {
            val result: Result<*> = Result<Any>()
            result.code = "0"
            result.msg = "成功"
            return result
        }

        fun <T> success(data: T): Result<T> {
            val result = Result(data)
            result.code = "0"
            result.msg = "成功"
            return result
        }

        fun <T> success(data: T, msg: String?): Result<T> {
            val result = Result(data)
            result.code = "0"
            result.msg = msg
            return result
        }

        fun <T> error(code: String?, msg: String?): Result<T> {
            val result= Result<T>()
            result.code = code
            result.msg = msg
            return result
        }
    }
}