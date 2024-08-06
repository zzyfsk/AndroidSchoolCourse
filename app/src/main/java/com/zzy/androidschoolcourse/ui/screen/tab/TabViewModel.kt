package com.zzy.androidschoolcourse.ui.screen.tab

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.base.util.FileName
import com.zzy.base.util.FileUtil

class TabViewModel(val context:Context):ScreenModel {
    private val fileUtil = FileUtil(context)

    val fileList = mutableStateListOf<FileName>()

    fun refresh(){
        fileList.clear()
        fileList.addAll(fileUtil.readFiles())
    }
}