package com.example.sportsoft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.sportsoft.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        val serviceIntent = Intent(this, HomeButtonService::class.java)
        startService(serviceIntent)
    }



    private fun showDialog(){
        MaterialAlertDialogBuilder(this).apply {
            setTitle("are you sure?")
            setMessage("want to close the application ?")
            setPositiveButton("Yes") { _, _ -> finish() }
            setNegativeButton("No", null)
            show()
        }
    }
}