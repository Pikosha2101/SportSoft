package com.example.sportsoft

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class NotificationSnackBar {
    @SuppressLint("RestrictedApi")
    fun showNotification(
        layoutInflater: LayoutInflater,
        layout: Int,
        root: View
    ){
        val customView = layoutInflater.inflate(
            layout,
            null
        )
        val snackBar = Snackbar.make(root, "", Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)

        val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.topMargin = 40
        snackBar.view.layoutParams = params
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }
}
