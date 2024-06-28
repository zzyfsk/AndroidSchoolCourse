package com.zzy.androidschoolcourse.util

class TimerUtil {

    fun getMinuteAndSecond(time:Int):String{
        val minute = time/60
        val second = time%60
        return "${if (minute<10) "0$minute" else minute}:${if (second<10) "0$second" else second}"
    }

    companion object{
        val timer = TimerUtil()
    }
}