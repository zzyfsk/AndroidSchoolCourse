package com.zzy.androidschoolcourse.service

import android.content.Context
import com.zzy.androidschoolcourse.util.AssertUtil

class QuestionService {

    fun readQuestion(context: Context, fileName: String): String {
        val inputStream = AssertUtil.assertUtil.readFile(context, fileName)
        val content = inputStream.bufferedReader().use { it.readLines() }
        inputStream.close()
        content.indices.random().also {
            return content[it].substring(0..3)
        }
    }

    companion object {
        val questionService = QuestionService()
    }
}