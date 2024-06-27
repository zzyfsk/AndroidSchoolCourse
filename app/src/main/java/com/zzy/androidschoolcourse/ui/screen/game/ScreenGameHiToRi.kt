package com.zzy.androidschoolcourse.ui.screen.game

import android.util.Log
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.compoment.NumberButton
import com.zzy.androidschoolcourse.ui.compoment.SymbolButton
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
        LaunchedEffect(Unit) {
            scope.launch (Dispatchers.IO){
                screenModel.setUpTimeSchedule()
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            BarTitle()
            Text(text = screenModel.time.toString())
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            NumberButtonGroup()
            BarSymbol()
            BarAction()
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
        var click1 by remember { mutableStateOf(false) }
        var click2 by remember { mutableStateOf(false) }
        var click3 by remember { mutableStateOf(false) }
        var click4 by remember { mutableStateOf(false) }

        val onClick: (Int) -> Unit = {
            click1 = it == 1
            click2 = it == 2
            click3 = it == 3
            click4 = it == 4
            Log.d("TAG", "$it $click1 $click2 $click3 $click4 ")
        }

        val backgroundColor: (Int) -> Color = {
            when (it) {
                1 -> if (click1) MainColor.GameButtonPress else MainColor.GameButtonUnPress
                2 -> if (click2) MainColor.GameButtonPress else MainColor.GameButtonUnPress
                3 -> if (click3) MainColor.GameButtonPress else MainColor.GameButtonUnPress
                4 -> if (click4) MainColor.GameButtonPress else MainColor.GameButtonUnPress
                else -> {
                    Color.Red
                }
            }
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
                    numberUp = 5,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(1),
                    onClick = onClick
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 2,
                    numberUp = 5,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(2),
                    onClick = onClick
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
                    numberUp = 5,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(3),
                    onClick = onClick
                )
                NumberButton(
                    modifier = Modifier
                        .weight(1f),
                    number = 4,
                    numberUp = 5,
                    length = 150,
                    fontSize = 50,
                    backgroundColor = backgroundColor(4),
                    onClick = onClick
                )
            }
        }
    }

    @Composable
    fun BarSymbol(
        modifier: Modifier = Modifier
    ) {
        var click1 by remember { mutableStateOf(false) }
        var click2 by remember { mutableStateOf(false) }
        var click3 by remember { mutableStateOf(false) }
        var click4 by remember { mutableStateOf(false) }

        val onClick: (Int) -> Unit = {
            click1 = it == 1
            click2 = it == 2
            click3 = it == 3
            click4 = it == 4
            Log.d("TAG", "$it $click1 $click2 $click3 $click4 ")
        }

        val backgroundColor: (Int) -> Color = {
            when (it) {
                1 -> if (click1) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
                2 -> if (click2) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
                3 -> if (click3) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
                4 -> if (click4) MainColor.SymbolButtonPress else MainColor.SymbolButtonUnPress
                else -> {
                    Color.Red
                }
            }
        }
        val symbolColor: (Int) -> Color = {
            when (it) {
                1 -> if (click1) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
                2 -> if (click2) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
                3 -> if (click3) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
                4 -> if (click4) MainColor.SymbolButtonUnPress else MainColor.SymbolButtonPress
                else -> {
                    Color.Red
                }
            }
        }
        BoxWithConstraints(modifier = modifier.fillMaxWidth()){
            val eachSize by remember {
                mutableStateOf(((maxWidth-20.dp)/4))
            }
            Row (modifier = Modifier){
                SymbolButton(modifier = modifier, number = 1,imageId = R.drawable.add, length = eachSize, backgroundColor = backgroundColor(1),symbolColor=symbolColor(1), onClick = onClick)
                SymbolButton(modifier = modifier, number = 2,imageId = R.drawable.del, length = eachSize, backgroundColor = backgroundColor(2),symbolColor=symbolColor(2), onClick = onClick)
                SymbolButton(modifier = modifier, number = 3,imageId = R.drawable.mul, length = eachSize, backgroundColor = backgroundColor(3),symbolColor=symbolColor(3), onClick = onClick)
                SymbolButton(modifier = modifier, number = 4,imageId = R.drawable.div, length = eachSize, backgroundColor = backgroundColor(4),symbolColor=symbolColor(4), onClick = onClick)

            }
        }
    }

    @Composable
    fun BarAction() {

    }
}