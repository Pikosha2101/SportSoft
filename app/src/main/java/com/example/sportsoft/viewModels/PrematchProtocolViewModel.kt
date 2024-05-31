package com.example.sportsoft.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrematchProtocolViewModel: ViewModel() {
    private val _firstTeamName = MutableLiveData<String?>()
    val firstTeamName: LiveData<String?> get() = _firstTeamName

    private val _secondTeamName = MutableLiveData<String?>()
    val secondTeamName: LiveData<String?> get() = _secondTeamName

    private val _firstTeamGoals = MutableLiveData<Int?>()
    val firstTeamGoals: LiveData<Int?> get() = _firstTeamGoals

    private val _secondTeamGoals = MutableLiveData<Int?>()
    val secondTeamGoals: LiveData<Int?> get() = _secondTeamGoals

    private val _matchActive = MutableLiveData<Boolean>()
    val matchActive: LiveData<Boolean> get() = _matchActive

    private val _matchIsLive = MutableLiveData<Int>()
    val matchIsLive: LiveData<Int> get() = _matchIsLive

    init {
        _firstTeamName.value = null
        _secondTeamName.value = null
        _firstTeamGoals.value = 0
        _secondTeamGoals.value = 0
        _matchActive.value = false
        _matchIsLive.value = 0
    }

    fun updateValues(
        firstTeamName: String,
        secondTeamName: String,
        firstTeamGoals: Int?,
        secondTeamGoals: Int?,
        matchActive: Boolean,
        matchIsLive: Int
    ) {
        _firstTeamName.value = firstTeamName
        _secondTeamName.value = secondTeamName
        _firstTeamGoals.value = firstTeamGoals
        _secondTeamGoals.value = secondTeamGoals
        _matchActive.value = matchActive
        _matchIsLive.value = matchIsLive
    }


    fun updateFirstTeamScoreCountPlus(
        firstTeamScore: Int?
    ) {
        _firstTeamGoals.value = firstTeamScore
    }

    fun changeScore(score: Int, isAddition: Boolean): Int {
        return if (isAddition){
            score + 1
        } else {
            if(score != 0){
                score - 1
            } else {
                score
            }
        }
    }

    fun updateSecondTeamScoreCountPlus(
        secondTeamScore: Int?
    ) {
        _secondTeamGoals.value = secondTeamScore
    }
}
