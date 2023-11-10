package com.example.ecconsol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


import com.example.ecconsol.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    var i = 0;

    private lateinit var binding: ActivityMainBinding


    private lateinit var navController :NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu);
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?,
            ) {

              title = when (destination.id) {
                R.id.cartFragment -> "My Cart"
                R.id.moreFragment -> "My Dashbord"
                else -> "E-Commwece"

            }
            }
        })


        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    i = 0;
                    navController.navigate(R.id.homeFragment)
                }
                1 -> i = 1
                2 -> i = 2
            }
            fun onBackPressed() {
                if (i == 0) {
                    finish()
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.header_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()
                true
            } R.id.logout -> {
                Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    }


