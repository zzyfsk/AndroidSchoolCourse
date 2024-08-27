package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.screen.ScreenTest
import com.zzy.androidschoolcourse.ui.screen.ScreenZlh
import com.zzy.androidschoolcourse.ui.screen.game.ScreenGameHiToRi
import com.zzy.androidschoolcourse.ui.screen.online.ScreenOnline


object ScreenTabMain: Tab{
    private fun readResolve(): Any = ScreenTabMain
    override val options: TabOptions
        @Composable
        get() {
            val title = "主页"
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.home2))

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent!!

        Column (modifier = Modifier.fillMaxSize()){
            Text(text = "This is Login Screen")
            Column {
                Button(onClick = { navigator.push(ScreenGameHiToRi()) }) {
                    Text(text = "单人游戏")
                }
                Button(onClick = { navigator.push(ScreenOnline()) }) {
                    Text(text = "多人游戏")
                }
                Button(onClick = { navigator.push(ScreenTest()) }) {
                    Text(text = "test Screen")
                }
                Button(onClick = { navigator.push(ScreenZlh()) }) {
                    Text(text = "zlh's screen")
                }
            }
        }
    }

}

