package com.example.sportsoft.Models

import com.example.sportsoft.IParam

data class EventModel(val eventName : String,
                      val playerName : String,
                      val assistantName : String?,
                      val minuteEvent: String) : IParam