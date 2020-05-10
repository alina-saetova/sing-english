package ru.itis.sing_english.presentation.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.itis.sing_english.R
import ru.itis.sing_english.di.AppInjector
import ru.itis.sing_english.presentation.view.ui.navigation.KeepStateNavigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.navFragment)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navFragment)!!
        val navigator = KeepStateNavigator(
            this, navHostFragment.childFragmentManager,
            R.id.navFragment
        )
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.navigation)

        nav_view.setupWithNavController(navController)
    }

    fun showBottomNavigation() {
        nav_view.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        nav_view.visibility = View.GONE
    }
}
