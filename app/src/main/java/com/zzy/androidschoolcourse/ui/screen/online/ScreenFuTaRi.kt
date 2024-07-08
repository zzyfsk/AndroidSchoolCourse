package com.zzy.androidschoolcourse.ui.screen.online

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.zzy.androidschoolcourse.ui.compoment.NumberButton
import com.zzy.androidschoolcourse.ui.screen.game.GameHiToRiViewModel
import com.zzy.androidschoolcourse.ui.theme.MainColor

class ScreenFuTaRi(val ip:String,val port:Int = 5123):Screen {
    @Composable
    override fun Content() {
        Text(text = "多人游戏界面")
    }

    @Composable
    fun BarTop(modifier: Modifier = Modifier){

    }

    @Composable
    fun GameYou(){

    }

    @Composable
    fun GameMe(){

    }

    @Composable
    fun NumberButtonGroup(modifier: Modifier = Modifier) {
        Log.d("tag", "Content: ButtonGroup")

        val viewModel = rememberScreenModel {
            FuTaRiViewModel()
        }

        val backgroundColor: (Int) -> Color = {
            if (it == viewModel.firstNumber) MainColor.GameButtonPress
            else MainColor.GameButtonUnPress
        }
        Column(modifier = modifier
            .height(370.dp)
            .padding(horizontal = 10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 1,
                    numberUp = viewModel.numberStateList[0].fraction.numerator,
                    numberDown = viewModel.numberStateList[0].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(1),
                    visible = viewModel.numberStateList[0].numberVisible,
                    onClick = viewModel.numberClick
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 2,
                    numberUp = viewModel.numberStateList[1].fraction.numerator,
                    numberDown = viewModel.numberStateList[1].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(2),
                    visible = viewModel.numberStateList[1].numberVisible,
                    onClick = viewModel.numberClick
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
                    numberUp = viewModel.numberStateList[2].fraction.numerator,
                    numberDown = viewModel.numberStateList[2].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(3),
                    visible = viewModel.numberStateList[2].numberVisible,
                    onClick = viewModel.numberClick
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 4,
                    numberUp =viewModel.numberStateList[3].fraction.numerator,
                    numberDown = viewModel.numberStateList[3].fraction.denominator,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(4),
                    visible = viewModel.numberStateList[3].numberVisible,
                    onClick = viewModel.numberClick
                )
            }
        }
    }
}