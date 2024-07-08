package com.zzy.androidschoolcourse.ui.screen.online

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.bean.Fraction

class FuTaRiViewModel:ScreenModel {

    // game logic
    var firstNumber by mutableIntStateOf(0)
    private var secondNumber = 0
    var currentSymbol by mutableIntStateOf(0)
    private var addCount = 0


    val numberClick: (Int) -> Unit = { number ->
        if (currentSymbol != 0 && firstNumber != 0) {
            clickSecondNumber(number)
        } else {
            clickFirstNumber(number)
        }
        winCheck()
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

    private fun winCheck(){
        if (addCount == 3){
            numberStateList.forEach {
                if (it.numberVisible && it.fraction.getInteger()==24 && it.fraction.getRemainder()==0){
//                    winShow = true
//                    Log.d(tag, "winCheck: win")
                }
            }
        }
    }

    // getNumberFromNet
    private fun initNumber(){
      TODO()
    }

    data class ButtonState(
        var fraction: Fraction = Fraction(1, 1),
        var numberVisible: Boolean = true
    )
}