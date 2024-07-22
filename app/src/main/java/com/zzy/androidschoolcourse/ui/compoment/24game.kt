package com.zzy.androidschoolcourse.ui.compoment

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.bean.Fraction
import com.zzy.androidschoolcourse.ui.theme.MainColor
import kotlinx.serialization.Serializable

@Composable
fun TwentyFourGame(
    modifier: Modifier = Modifier,
    win: () -> Unit = {},
    click: (TwentyFourGameState) -> Unit = {},
    initNumber: String,
) {
    var firstNumber by remember { mutableIntStateOf(0) }
    var secondNumber by remember { mutableIntStateOf(0) }
    var currentSymbol by remember { mutableIntStateOf(0) }
    var addCount by remember { mutableIntStateOf(0) }
    val numberStateList = remember { mutableStateListOf<TwentyFourGameButtonState>() }

    fun getNumberState(): SnapshotStateList<TwentyFourGameButtonState> {
        if (numberStateList.isEmpty()) {
            for (i in 0..3) {
                numberStateList.add(TwentyFourGameButtonState())
            }
        }
        return numberStateList
    }


    fun initNumber() {
        getNumberState().forEachIndexed { index, _ ->
            initNumber[index].digitToInt().let {
                numberStateList[index].fraction.numerator = if (it == 0) 10 else it
                numberStateList[index].fraction.denominator = 1
            }
        }
    }


    val winCheck: () -> Unit = {
        if (addCount == 3) {
            getNumberState().forEach {
                if (it.numberVisible && it.fraction.getInteger() == 24 && it.fraction.getRemainder() == 0) {
                    win()
                }
            }
        }
    }

    val clickFirstNumber: (Int) -> Unit = { number ->
        firstNumber = number
        currentSymbol = 0
    }

    val clickSecondNumber: (Int) -> Unit = { number ->
        if (number != firstNumber) {
            addCount++
            secondNumber = number
            getNumberState()[firstNumber - 1].numberVisible = false

            when (currentSymbol) {
                1 -> numberStateList[secondNumber - 1].fraction += numberStateList[firstNumber - 1].fraction
                2 -> numberStateList[secondNumber - 1].fraction =
                    numberStateList[firstNumber - 1].fraction - numberStateList[secondNumber - 1].fraction

                3 -> numberStateList[secondNumber - 1].fraction *= numberStateList[firstNumber - 1].fraction
                4 -> numberStateList[secondNumber - 1].fraction =
                    numberStateList[firstNumber - 1].fraction / numberStateList[secondNumber - 1].fraction
            }


            firstNumber = 0
            secondNumber = 0
            currentSymbol = 0
        }

    }

    val numberClick: (Int) -> Unit = {
        if (currentSymbol != 0 && firstNumber != 0) {
            clickSecondNumber(it)
        } else {
            clickFirstNumber(it)
        }
        winCheck()
        click(
            TwentyFourGameState(
                firstNumber,
                secondNumber,
                currentSymbol,
                addCount,
                getNumberState(),
                initNumber
            )
        )
    }

    fun resetGame() {
        initNumber()
        for (i in 0..3) getNumberState()[i].numberVisible = true
        firstNumber = 0
        secondNumber = 0
        currentSymbol = 0
        addCount = 0
        numberStateList.add(TwentyFourGameButtonState())
        numberStateList.removeLast()
    }

    val backgroundColor: (Int) -> Color = {
        if (it == firstNumber) MainColor.GameButtonPress
        else MainColor.GameButtonUnPress
    }

    LaunchedEffect(key1 = initNumber) {
        resetGame()
        addCount = 0
    }

    Column(modifier = Modifier) {
        Column(
            modifier = Modifier
                .height(370.dp)
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 1,
                    numberUp = getNumberState()[0].fraction.numerator,
                    numberDown = numberStateList[0].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(1),
                    visible = numberStateList[0].numberVisible,
                    onClick = numberClick
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 2,
                    numberUp = numberStateList[1].fraction.numerator,
                    numberDown = numberStateList[1].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(2),
                    visible = numberStateList[1].numberVisible,
                    onClick = numberClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 3,
                    numberUp = numberStateList[2].fraction.numerator,
                    numberDown = numberStateList[2].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(3),
                    visible = numberStateList[2].numberVisible,
                    onClick = numberClick
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 4,
                    numberUp = numberStateList[3].fraction.numerator,
                    numberDown = numberStateList[3].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(4),
                    visible = numberStateList[3].numberVisible,
                    onClick = numberClick
                )
            }
        }

        val symbolBackgroundColor: (Int) -> Color = {
            if (it == currentSymbol) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
        }
        val symbolColor: (Int) -> Color = {
            if (it == currentSymbol) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
        }
        val symbolClick: (Int) -> Unit = {
            currentSymbol = if (it == currentSymbol) 0 else it
            click(
                TwentyFourGameState(
                    firstNumber,
                    secondNumber,
                    currentSymbol,
                    addCount,
                    getNumberState(),
                    initNumber
                )
            )
        }

        BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
            val eachSize by remember {
                mutableStateOf(((maxWidth - 20.dp) / 4))
            }
            Row(modifier = Modifier) {
                SymbolButton(
                    modifier = modifier,
                    number = 1,
                    imageId = R.drawable.add,
                    length = eachSize,
                    backgroundColor = symbolBackgroundColor(1),
                    symbolColor = symbolColor(1),
                    onClick = symbolClick
                )
                SymbolButton(
                    modifier = modifier,
                    number = 2,
                    imageId = R.drawable.del,
                    length = eachSize,
                    backgroundColor = symbolBackgroundColor(2),
                    symbolColor = symbolColor(2),
                    onClick = symbolClick
                )
                SymbolButton(
                    modifier = modifier,
                    number = 3,
                    imageId = R.drawable.mul,
                    length = eachSize,
                    backgroundColor = symbolBackgroundColor(3),
                    symbolColor = symbolColor(3),
                    onClick = symbolClick
                )
                SymbolButton(
                    modifier = modifier,
                    number = 4,
                    imageId = R.drawable.div,
                    length = eachSize,
                    backgroundColor = symbolBackgroundColor(4),
                    symbolColor = symbolColor(4),
                    onClick = symbolClick
                )

            }
        }
        Button(onClick = { resetGame() }) {
            Text(text = "Reset")
        }
    }
}

@Composable
fun TwentyFourGameView(
    gameViewModel: TwentyFourGameState,
    modifier: Modifier = Modifier
) {
    fun getNumberState(): MutableList<TwentyFourGameButtonState> {
        return gameViewModel.numberStateList
    }

    val backgroundColor: (Int) -> Color = {
        if (it == gameViewModel.currentSymbol) MainColor.GameButtonPress
        else MainColor.GameButtonUnPress
    }

    val symbolBackgroundColor: (Int) -> Color = {
        if (it == gameViewModel.currentSymbol) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
    }
    val symbolColor: (Int) -> Color = {
        if (it == gameViewModel.currentSymbol) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
    }

    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .height(350.dp)
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 1,
                    numberUp = getNumberState()[0].fraction.numerator,
                    numberDown = getNumberState()[0].fraction.denominator,
                    length = 130,
                    fontSize = 50,
                    backgroundColor = backgroundColor(1),
                    visible = getNumberState()[0].numberVisible,
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 3,
                    numberUp = getNumberState()[2].fraction.numerator,
                    numberDown = getNumberState()[2].fraction.denominator,
                    length = 130,
                    fontSize = 50,
                    backgroundColor = backgroundColor(3),
                    visible = getNumberState()[2].numberVisible,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 2,
                    numberUp = getNumberState()[1].fraction.numerator,
                    numberDown = getNumberState()[1].fraction.denominator,
                    length = 130,
                    fontSize = 50,
                    backgroundColor = backgroundColor(2),
                    visible = getNumberState()[1].numberVisible,
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 4,
                    numberUp = getNumberState()[3].fraction.numerator,
                    numberDown = getNumberState()[3].fraction.denominator,
                    length = 130,
                    fontSize = 50,
                    backgroundColor = backgroundColor(4),
                    visible = getNumberState()[3].numberVisible,
                )
            }
            BoxWithConstraints(modifier = modifier
                .fillMaxHeight()
                .weight(0.5f)) {
                val eachSize by remember {
                    mutableStateOf(((maxHeight - 20.dp) / 4))
                }
                Column(modifier = Modifier) {
                    SymbolButton(
                        modifier = modifier,
                        number = 1,
                        imageId = R.drawable.add,
                        length = eachSize,
                        backgroundColor = symbolBackgroundColor(1),
                        symbolColor = symbolColor(1)
                    )
                    SymbolButton(
                        modifier = modifier,
                        number = 2,
                        imageId = R.drawable.del,
                        length = eachSize,
                        backgroundColor = symbolBackgroundColor(2),
                        symbolColor = symbolColor(2)
                    )
                    SymbolButton(
                        modifier = modifier,
                        number = 3,
                        imageId = R.drawable.mul,
                        length = eachSize,
                        backgroundColor = symbolBackgroundColor(3),
                        symbolColor = symbolColor(3)
                    )
                    SymbolButton(
                        modifier = modifier,
                        number = 4,
                        imageId = R.drawable.div,
                        length = eachSize,
                        backgroundColor = symbolBackgroundColor(4),
                        symbolColor = symbolColor(4)
                    )

                }
            }
        }


    }

}

@Serializable
data class TwentyFourGameButtonState(
    var fraction: Fraction = Fraction(1, 1),
    var numberVisible: Boolean = true
)

@Serializable
data class TwentyFourGameState(
    val firstNumber: Int = 0,
    val secondNumber: Int = 0,
    val currentSymbol: Int = 0,
    val addCount: Int = 0,
    val numberStateList: MutableList<TwentyFourGameButtonState> = mutableStateListOf(),
    val numbers: String = "0000"
) {
    override fun toString(): String {
        return "$firstNumber,$secondNumber,$currentSymbol,$addCount"
    }

    init {
        for (i in 0..3) {
            numberStateList.add(TwentyFourGameButtonState())
        }
    }
}