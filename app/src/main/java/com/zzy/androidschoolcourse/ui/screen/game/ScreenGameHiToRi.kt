package com.zzy.androidschoolcourse.ui.screen.game

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.compoment.NumberButton
import com.zzy.androidschoolcourse.ui.compoment.SymbolButton
import com.zzy.androidschoolcourse.ui.screen.win.ScreenWin
import com.zzy.androidschoolcourse.ui.theme.MainColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenGameHiToRi : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val screenModel = rememberScreenModel {
            GameHiToRiViewModel()
        }
        val navigator = LocalNavigator.current
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            screenModel.init(context)
            scope.launch (Dispatchers.IO){
                screenModel.setUpTimeSchedule()
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            BarTitle()
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            Timer()
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            NumberButtonGroup()
            BarSymbol()
            BarAction()
            if (screenModel.winShow){
                screenModel.winShow = false
                navigator?.replace(ScreenWin())
            }
        }
    }

    @Composable
    fun Timer(modifier: Modifier = Modifier) {
        val screenModel = rememberScreenModel {
            GameHiToRiViewModel()
        }
        Row (modifier = modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.weight(1f))
            Text(modifier = Modifier.requiredWidth(60.dp),text = screenModel.time)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BarTitle(modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current
        TopAppBar(
            modifier = modifier.fillMaxWidth(),
            title = { Text(text = "经典模式") },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(100))
                        .clickable { navigator?.pop() },
                    imageVector = ImageVector.vectorResource(id = R.drawable.back),
                    contentDescription = ""
                )
            }

        )
    }

    @Composable
    fun NumberButtonGroup(modifier: Modifier = Modifier) {
        Log.d("tag", "Content: ButtonGroup")

        val viewModel = rememberScreenModel {
            GameHiToRiViewModel()
        }

        val backgroundColor: (Int) -> Color = {
            if (it == viewModel.firstNumber)MainColor.GameButtonPress
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

    @Composable
    fun BarSymbol(
        modifier: Modifier = Modifier
    ) {
        val viewModel = rememberScreenModel {
            GameHiToRiViewModel()
        }

        val backgroundColor: (Int) -> Color = {
            if (it == viewModel.currentSymbol) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
        }
        val symbolColor: (Int) -> Color = {
            if (it == viewModel.currentSymbol) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
        }
        BoxWithConstraints(modifier = modifier.fillMaxWidth()){
            val eachSize by remember {
                mutableStateOf(((maxWidth-20.dp)/4))
            }
            Row (modifier = Modifier){
                SymbolButton(modifier = modifier, number = 1,imageId = R.drawable.add, length = eachSize, backgroundColor = backgroundColor(1),symbolColor=symbolColor(1), onClick = viewModel.symbolClick)
                SymbolButton(modifier = modifier, number = 2,imageId = R.drawable.del, length = eachSize, backgroundColor = backgroundColor(2),symbolColor=symbolColor(2), onClick = viewModel.symbolClick)
                SymbolButton(modifier = modifier, number = 3,imageId = R.drawable.mul, length = eachSize, backgroundColor = backgroundColor(3),symbolColor=symbolColor(3), onClick = viewModel.symbolClick)
                SymbolButton(modifier = modifier, number = 4,imageId = R.drawable.div, length = eachSize, backgroundColor = backgroundColor(4),symbolColor=symbolColor(4), onClick = viewModel.symbolClick)

            }
        }
    }

    @Composable
    fun BarAction(modifier: Modifier = Modifier) {
        val viewModel = rememberScreenModel {
            GameHiToRiViewModel()
        }

        Row (modifier = modifier.fillMaxWidth()){
            Button(onClick = { viewModel.resetGame() }) {
                Text(text = "reset")
            }
        }
    }
}