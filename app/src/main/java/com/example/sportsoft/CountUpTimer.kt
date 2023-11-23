package com.example.sportsoft

import android.os.CountDownTimer

abstract class CountUpTimer(private val countUpInterval: Long) : CountDownTimer(Long.MAX_VALUE, countUpInterval) {
    private var elapsedTime: Long = 0

    override fun onTick(millisUntilFinished: Long) {
        elapsedTime += countUpInterval
        onCountUpTick(elapsedTime)
    }

    override fun onFinish() {
        // Do nothing
    }

    abstract fun onCountUpTick(elapsedTime: Long)

    fun startTimer() {
        super.start()
    }
}