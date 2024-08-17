package com.zzy.androidschoolcourse.ui.screen.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zzy.androidschoolcourse.R
import com.zzy.androidschoolcourse.ui.screen.history.ScreenHistory
import com.zzy.base.util.FileName

object ScreenTabHistory : Tab {
    private fun readResolve(): Any = ScreenTabMain
    override val options: TabOptions
        @Composable
        get() {
            val title = "历史"
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.history))

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
        val navigator = LocalNavigator.currentOrThrow.parent!!
        val viewModel: TabViewModel = rememberScreenModel {
            TabViewModel(context)
        }
        viewModel.refresh()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.fileList) { file ->
                HistoryItem(
                    modifier = Modifier.height(40.dp),
                    fileName = file,
                    navigator = navigator,
                    viewModel = viewModel
                )
                HorizontalDivider()
            }
        }
    }

    @Composable
    fun HistoryItem(
        modifier: Modifier,
        fileName: FileName,
        navigator: Navigator,
        viewModel: TabViewModel
    ) {
        val icon = if (fileName.fileName[0].digitToInt() == 1)
            R.drawable.person else R.drawable.group

        Row(modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigator.push(ScreenHistory(fileName.fileName))
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 5.dp),
                painter = painterResource(id = icon),
                contentDescription = "history"
            )
            Text(text = fileName.fileName)
            Button(onClick = { viewModel.delete(fileName) }) {
                Text(text = "删除记录")
            }
        }
    }

}