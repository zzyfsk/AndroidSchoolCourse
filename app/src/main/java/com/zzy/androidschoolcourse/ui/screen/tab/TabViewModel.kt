package com.zzy.androidschoolcourse.ui.screen.tab

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.base.util.FileName
import com.zzy.base.util.FileUtil

class TabViewModel(val context: Context) : ScreenModel {
    private val fileUtil = FileUtil(context)

    val fileList = mutableStateListOf<FileName>()

    var information by mutableStateOf(MineMiddleInformation())

    fun updateInformation(
        updateFriend: () -> Int,
        updateApply: () -> Int,
        updateWill: () -> Int,
        updateScore: () -> Int
    ) {
        information = information.copy(
            friendNumber = updateFriend(),
            applyNumber = updateApply(),
            willNumber = updateWill(),
            score = updateScore()
        )
    }

    fun refresh() {
        fileList.clear()
        fileList.addAll(fileUtil.readFiles())
    }

    fun delete(fileName: FileName) {
        fileList.find { it.fileName == fileName.fileName }?.let {
            fileList.remove(fileName)
            fileUtil.deleteFile(fileName)
        }
    }
}