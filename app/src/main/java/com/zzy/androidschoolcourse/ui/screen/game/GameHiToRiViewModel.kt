package com.zzy.androidschoolcourse.ui.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import java.util.Timer
import java.util.TimerTask

class GameHiToRiViewModel : ScreenModel {

    var time by mutableIntStateOf(0)


    fun setUpTimeSchedule() {
        val timer = Timer()

        val timerTask = object : TimerTask() {
            override fun run() {
                time++
            }
        }

        timer.schedule(timerTask,1000,1000)
    }
}