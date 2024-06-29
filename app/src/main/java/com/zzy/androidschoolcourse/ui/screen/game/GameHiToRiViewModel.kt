package com.zzy.androidschoolcourse.ui.screen.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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

    var addCount = 0

    fun setUpTimeSchedule() {
        val timer = Timer()

        val timerTask = object : TimerTask() {
            override fun run() {
                time = TimerUtil.timer.getMinuteAndSecond(timeValue++)
            }
        }

        timer.schedule(timerTask, 1000, 1000)
    }

    val numberStateList = mutableStateListOf<ButtonState>()
        get() {
            if (field.isEmpty()) {
                for (i in 0..3) {
                    field.add(ButtonState())
                }
            }
            return field
        }

    // game logic
    private var initNumber = ""


    var firstNumber by mutableIntStateOf(0)
    private var secondNumber = 0
    var currentSymbol by mutableIntStateOf(0)

    private fun readInitNumber(context: Context) {
        initNumber =
            QuestionService.questionService.readQuestion(
                context = context,
                fileName = "dataSet.txt"
            )
    }

    private fun initNumber() {
        numberStateList.forEachIndexed { index, _ ->
            initNumber[index].digitToInt().let {
                numberStateList[index].fraction.numerator = if (it==0) 10 else it
                numberStateList[index].fraction.denominator = 1
            }
        }
    }

    val numberClick: (Int) -> Unit = { number ->
        if (currentSymbol != 0 && firstNumber != 0) {
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

        if (number ==firstNumber){
            return
        }else{
            addCount++
            secondNumber = number
            numberStateList[firstNumber - 1].numberVisible = false

            when (currentSymbol) {
                1 -> numberStateList[secondNumber - 1].fraction += numberStateList[firstNumber - 1].fraction
                2 -> numberStateList[secondNumber-1].fraction = numberStateList[firstNumber-1].fraction - numberStateList[secondNumber-1].fraction
                3 -> numberStateList[secondNumber - 1].fraction *= numberStateList[firstNumber - 1].fraction
                4 -> numberStateList[secondNumber-1].fraction = numberStateList[firstNumber-1].fraction / numberStateList[secondNumber-1].fraction
            }


            firstNumber = 0
            secondNumber = 0
            currentSymbol = 0
        }
    }

    val symbolClick: (Int) -> Unit = {
        currentSymbol = if (it == currentSymbol) 0 else it
        Log.d(tag, ": $currentSymbol")
    }

    fun resetGame() {
        initNumber()
        for (i in 0..3) numberStateList[i].numberVisible = true
        firstNumber = 0
        secondNumber = 0
        currentSymbol = 0
        addCount = 0
        numberStateList.add(ButtonState())
        numberStateList.removeLast()
    }

    fun init(context: Context) {
        readInitNumber(context = context)
        initNumber()
        addCount = 0
    }

    fun winCheck(){
        if (addCount == 3){
            numberStateList.forEach {
                if (it.numberVisible && it.fraction.getInteger()==24 && it.fraction.getRemainder()==0){
                    Log.d(tag, "winCheck: win")
                }
            }
        }
    }

    data class ButtonState(
        var fraction: Fraction = Fraction(1, 1),
        var numberVisible: Boolean = true
    )
}