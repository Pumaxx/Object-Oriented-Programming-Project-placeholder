package com.example.timersinwindows

import android.os.CountDownTimer

class Timers {
    lateinit var countdown_timer: CountDownTimer
    var currentTime: String ="10"
    var timeInMiliSeconds = 0L

    fun pauseTimer() {
        countdown_timer.cancel()
    }

    fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
            }

            override fun onTick(p0: Long) {
                timeInMiliSeconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()
    }

     fun updateTextUI() {
        val minute = (timeInMiliSeconds / 1000) / 60
        val seconds = (timeInMiliSeconds / 1000) % 60

        if(seconds<10)
            currentTime = "$minute:0$seconds"
        else
            currentTime= "$minute:$seconds"
    }
}

