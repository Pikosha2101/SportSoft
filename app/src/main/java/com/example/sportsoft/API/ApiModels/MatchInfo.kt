package com.example.sportsoft.API.ApiModels

import com.example.sportsoft.IParam

data class MatchInfo(
    val start_dt: String,
    val team1_shortname: String,
    val team2_shortname: String,
    val gf: Int?,
    val ga: Int?,
    val active: Boolean,
    val is_live: Int
) : IParam