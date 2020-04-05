package ru.itis.sing_english

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.di.Injectable
import ru.itis.sing_english.view.ui.navigation.KeepStateNavigator
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity(),
    HasAndroidInjector,
    Injectable {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.navFragment)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navFragment)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.navFragment)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.navigation)

        nav_view.setupWithNavController(navController)

//        val appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf (
//                R.id.navigation_fav,
//                R.id.navigation_main,
//                R.id.navigation_vocab
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    fun showBottomNavigation() {
        nav_view.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        nav_view.visibility = View.GONE
    }
}
