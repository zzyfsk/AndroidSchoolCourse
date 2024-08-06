package com.zzy.base.util

import android.icu.util.Calendar

class TimeUtil {
    private val calendar = Calendar.getInstance()

    fun getTime():String{
        return "${calendar.get(Calendar.YEAR)}_" +
                "${calendar.get(Calendar.MONTH)}_" +
                "${calendar.get(Calendar.DAY_OF_MONTH)}_" +
                "${calendar.get(Calendar.HOUR)}_" +
                "${calendar.get(Calendar.MINUTE)}_" +
                "${calendar.get(Calendar.SECOND)}"
    }

    companion object{
        val timeUtil = TimeUtil()
    }
}