package com.zzy.androidschoolcourse.util

import android.content.Context
import java.io.IOException
import java.io.InputStream

class AssertUtil {

    fun readFile(context: Context, fileName: String): InputStream {
        val assertManager = context.assets
        try {
            return assertManager.open(fileName)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    companion object {
        val assertUtil = AssertUtil()
    }
}