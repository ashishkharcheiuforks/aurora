package com.czxbnb.aurora.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.databinding.ActivityMainBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.ui.activity.ActivityFragment
import com.czxbnb.aurora.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding> (MainViewModel::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding.viewModel = viewModel
        // Define the bottom navigation listener
        navigationView.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.navigation_home -> {
                    // Jump to home fragment
                    val homeFragment = HomeFragment()
                    openFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_news -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_activity -> {
                    val activityFragment = ActivityFragment()
                    openFragment(activityFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    throw IllegalArgumentException("Menu item not exist")
                }
            }
        }

        // Set default selected view
        navigationView.selectedItemId = R.id.navigation_home
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    /**
     * Start a new fragment, and replace the existing one
     * @param fragment fragment that required to be launched
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
