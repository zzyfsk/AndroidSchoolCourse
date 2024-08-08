package com.zzy.androidschoolcourse.ui.screen.history

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.zzy.androidschoolcourse.ui.component.GameMode
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameRecord
import com.zzy.androidschoolcourse.ui.component.TwentyFourGameState
import com.zzy.base.util.FileUtil
import kotlinx.serialization.json.Json

class HistoryViewModel(context: Context) : ScreenModel {
    private val fileUtil = FileUtil(context)

    /**
     * @see 1为单人2为多人
     */
    var showType by mutableIntStateOf(0)

    private var currentRecord by mutableStateOf(TwentyFourGameRecord(GameMode.NULL))
    var current1 by mutableIntStateOf(0)
    var current2 by mutableIntStateOf(0)
    var currentState1 by mutableStateOf(TwentyFourGameState(numbers = "1111"))
    var currentState2 by mutableStateOf(TwentyFourGameState(numbers = "1111"))

    private fun readFile(fileName: String) {
        val file = fileUtil.readFile(fileName)
        showType = fileName[0].digitToInt()
        val content = file.readText()
        currentRecord = Json.decodeFromString(content)

    }

    fun previous1() {
        if (current1 <= 0) current1 = 0
        else current1--
        currentState1 = currentRecord.recordList[current1]
    }

    fun next1() {
        if (current1 >= currentRecord.recordList.size - 1) current1 =
            currentRecord.recordList.size - 1
        else current1++
        currentState1 = currentRecord.recordList[current1]
    }

    fun previous2() {
        if (current2 <= 0) current2 = 0
        else current2--
        currentState2 = currentRecord.recordList2[current2]
    }

    fun next2() {
        if (current2 >= currentRecord.recordList2.size - 1) current2 =
            currentRecord.recordList2.size - 1
        else current2++
        currentState2 = currentRecord.recordList2[current2]
    }

    fun init(fileName: String) {
        readFile(fileName)

        current1 = 0
        currentState1 = currentRecord.recordList[current1]
        if (currentRecord.gameMode == GameMode.HuTaRi) {
            current2 = 0
            currentState2 = currentRecord.recordList2[current2]
        }
    }
}