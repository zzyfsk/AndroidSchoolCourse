package com.zzy.androidschoolcourse.ui.screen.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.bean.Fraction
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
    val fraction1 by mutableStateOf(Fraction(1, 1))
    val fraction2 by mutableStateOf(Fraction(1, 1))
    val fraction3 by mutableStateOf(Fraction(1, 1))
    val fraction4 by mutableStateOf(Fraction(1, 1))

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

        initNumber[0].digitToInt().let { if (it == 0) fraction1.numerator = 10 else fraction1.numerator = it }
        initNumber[1].digitToInt().let { if (it == 0) fraction2.numerator = 10 else fraction2.numerator = it }
        initNumber[2].digitToInt().let { if (it == 0) fraction3.numerator = 10 else fraction3.numerator = it }
        initNumber[3].digitToInt().let { if (it == 0) fraction4.numerator = 10 else fraction4.numerator = it }

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