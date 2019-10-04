package com.czxbnb.aurora.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define the bottom navigation listener
        navigationView.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.navigation_home -> {
                    // Jump to home fragment
                    val homeFragment = HomeFragment.newInstance()
                    openFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_news -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_activity -> {
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
