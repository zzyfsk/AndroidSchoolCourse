package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zzy.androidschoolcourse.R

object ScreenTabHistory:Tab {
    private fun readResolve(): Any = ScreenTabMain
    override val options: TabOptions
        @Composable
        get() {
            val title = "历史"
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.history))

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
        val context = LocalContext.current
        val viewModel:TabViewModel = rememberScreenModel {
            TabViewModel(context)
        }
        LazyColumn (modifier = Modifier.fillMaxSize()){
            items(viewModel.fileList){ file->
                HistoryItem(modifier = Modifier.height(30.dp), fileName = file.fileName )
            }
        }
    }

    @Composable
    fun HistoryItem(modifier: Modifier,fileName:String){
        Row (modifier = modifier, verticalAlignment = Alignment.CenterVertically){
            Text(text = fileName)
        }
    }

}