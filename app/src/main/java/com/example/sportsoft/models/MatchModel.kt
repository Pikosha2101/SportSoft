package com.example.sportsoft.models

import com.example.sportsoft.IParam

data class MatchModel(
    var date : String,
    var firstTeamName : String,
    var secondTeamName : String,
    var firstTeamScore: Int,
    var secondTeamScore: Int
) : IParam
