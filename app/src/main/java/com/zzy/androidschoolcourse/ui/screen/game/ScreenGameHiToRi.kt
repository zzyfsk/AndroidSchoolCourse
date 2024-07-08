package com.zzy.androidschoolcourse.ui.screen.game

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.compoment.TwentyFourGame
import com.zzy.androidschoolcourse.ui.screen.win.ScreenWin
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
            TwentyFourGame(win = { screenModel.winShow = true }, initNumber = screenModel.initNumber, click = {
                Log.d("tag", "Content: $it")
            })
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

}