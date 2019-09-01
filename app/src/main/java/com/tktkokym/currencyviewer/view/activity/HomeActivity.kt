package com.tktkokym.currencyviewer.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.tktkokym.currencyviewer.R
import com.tktkokym.currencyviewer.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        navController = Navigation.findNavController(this, R.id.home_navigation_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        NavigationUI.setupActionBarWithNavController(this, navController) //setup action bar with navigation
        binding.navigationView.setupWithNavController(navController) //setup navigation
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}
