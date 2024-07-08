package com.zzy.androidschoolcourse.ui.screen.game

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.net.QuestionService
import com.zzy.androidschoolcourse.util.TimerUtil
import java.util.Timer
import java.util.TimerTask

class GameHiToRiViewModel : ScreenModel {

    var winShow by mutableStateOf(false)

    private val tag = "GameHiToRiViewModel"
    var timeValue = 1

    var time by mutableStateOf("00:00")

    private var addCount = 0

    // game logic
    var initNumber by mutableStateOf("0000")


    private fun readInitNumber(context: Context) {
        initNumber =
            QuestionService.questionService.readQuestion(
                context = context,
                fileName = "dataSet.txt"
            )
    }

    fun init(context: Context) {
        readInitNumber(context = context)
        addCount = 0
    }

    fun setUpTimeSchedule() {
        val timer = Timer()

        val timerTask = object : TimerTask() {
            override fun run() {
                time = TimerUtil.timer.getMinuteAndSecond(timeValue++)
            }
        }

        timer.schedule(timerTask, 1000, 1000)
    }
}