package com.example.sportsoft

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class HomeButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
            Toast.makeText(context, "ЗАГОТОВКА. Нажата кнопка \"домой\"", Toast.LENGTH_SHORT).show()
        }
    }
}