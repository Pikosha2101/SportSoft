package com.example.sportsoft.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsoft.CountUpTimer

class MatchProgressViewModel: ViewModel() {
    private val _isTimerRunning = MutableLiveData<Boolean>()
    val isTimerRunning: LiveData<Boolean>
        get() = _isTimerRunning

    private val _secondsRemaining = MutableLiveData<Long>()

    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String>
        get() = _currentTime

    private var countUpTimer: CountUpTimer? = null

    init {
        _isTimerRunning.value = false
        _secondsRemaining.value = 0
        _currentTime.value = "00:00" // Начальное значение времени
    }

    fun startTimer() {
        if (_isTimerRunning.value != true) {
            countUpTimer = object : CountUpTimer(1000) {
                override fun onCountUpTick(elapsedTime: Long) {
                    _secondsRemaining.value = elapsedTime / 1000
                    updateCurrentTime()
                }
            }
            countUpTimer?.startTimer()
            _isTimerRunning.value = true
        }
    }

    fun stopTimer() {
        if (_isTimerRunning.value == true) {
            countUpTimer?.cancel()
            _isTimerRunning.value = false
            _secondsRemaining.value = 0
            updateCurrentTime()
        }
    }

    private fun updateCurrentTime() {
        val minutes = _secondsRemaining.value?.div(60) ?: 0
        val seconds = _secondsRemaining.value?.rem(60) ?: 0
        _currentTime.value = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        countUpTimer?.cancel()
    }
}
