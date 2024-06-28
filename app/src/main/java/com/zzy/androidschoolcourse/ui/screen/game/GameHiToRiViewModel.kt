package com.zzy.androidschoolcourse.ui.screen.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.service.QuestionService
import com.zzy.androidschoolcourse.util.TimerUtil
import java.util.Timer
import java.util.TimerTask

class GameHiToRiViewModel : ScreenModel {

    private val tag = "GameHiToRiViewModel"
    var timeValue = 1

    var time by mutableStateOf("00:00")


    fun setUpTimeSchedule() {
        val timer = Timer()

        val timerTask = object : TimerTask() {
            override fun run() {
                time = TimerUtil.timer.getMinuteAndSecond(timeValue++)
            }
        }

        timer.schedule(timerTask, 1000, 1000)
    }

    // game logic
    private var initNumber = ""
    var number1Top by mutableIntStateOf(1)
    var number1Bottom by mutableIntStateOf(1)
    var number2Top by mutableIntStateOf(1)
    var number2Bottom by mutableIntStateOf(1)
    var number3Top by mutableIntStateOf(1)
    var number3Bottom by mutableIntStateOf(1)
    var number4Top by mutableIntStateOf(1)
    var number4Bottom by mutableIntStateOf(1)

    var number1Visible by mutableStateOf(true)
    var number2Visible by mutableStateOf(true)
    var number3Visible by mutableStateOf(true)
    var number4Visible by mutableStateOf(true)

    var firstNumber by mutableIntStateOf(0)
    private var secondNumber = 0
    var currentSymbol by mutableIntStateOf(0)

    fun readInitNumber(context: Context, fileName: String) {
        initNumber =
            QuestionService.questionService.readQuestion(context = context, fileName = fileName)
        number1Top = initNumber[0].digitToInt()
        number2Top = initNumber[1].digitToInt()
        number3Top = initNumber[2].digitToInt()
        number4Top = initNumber[3].digitToInt()
        if (number1Top == 0) number1Top = 10
        if (number2Top == 0) number2Top = 10
        if (number3Top == 0) number3Top = 10
        if (number4Top == 0) number4Top = 10
    }

    val numberClick: (Int) -> Unit = { number ->
        if (currentSymbol != 0) {
            clickSecondNumber(number)
        } else {
            clickFirstNumber(number)
        }
    }

    private fun clickFirstNumber(number: Int) {
        firstNumber = number
        currentSymbol = 0
    }

    private fun clickSecondNumber(number: Int) {
        secondNumber = number
        when (firstNumber) {
            1 -> number1Visible = false
            2 -> number2Visible = false
            3 -> number3Visible = false
            4 -> number4Visible = false
        }
        firstNumber = 0
        secondNumber = 0
        currentSymbol = 0
    }

    val symbolClick: (Int) -> Unit = {
        currentSymbol = if (it == currentSymbol) 0 else it
        Log.d(tag, ": $currentSymbol")
    }
}