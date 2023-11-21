package com.example.sportsoft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.sportsoft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authorizationFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }

        binding.menuImageButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)

            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.matchRegister -> {
                        navController.navigate(R.id.matchRegisterFragment)
                        true
                    }
                    R.id.exit -> {
                        navController.navigate(R.id.authorizationFragment)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }



    private fun showBottomNav() {
        binding.headerConstraint.visibility = View.VISIBLE
    }



    private fun hideBottomNav() {
        binding.headerConstraint.visibility = View.GONE
    }
}