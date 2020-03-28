package ru.itis.sing_english

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.view.ui.FavouritesFragment
import ru.itis.sing_english.view.ui.MainPageFragment
import ru.itis.sing_english.view.ui.VocabularyFragment

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity(),  BottomNavigationView.OnNavigationItemSelectedListener   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbar)
        nav_view.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MainPageFragment.newInstance(), MainPageFragment::class.java.simpleName)
                .addToBackStack(MainPageFragment.newInstance().javaClass.simpleName)
                .commit()
        }
        nav_view.selectedItemId = R.id.navigation_main
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var title = ""
        var fragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.navigation_fav -> {
                title = "Favourite"
                fragment = FavouritesFragment.newInstance()
            }
            R.id.navigation_main -> {
                title = "Main"
                fragment = MainPageFragment.newInstance()
            }
            R.id.navigation_vocab -> {
                title = "Vocabulary"
                fragment = VocabularyFragment.newInstance()
            }
        }
        supportActionBar?.title = title
        supportFragmentManager.also {
            it.beginTransaction().apply {
                if (fragment != null) {
                    replace(R.id.main_container, fragment)
                }
                addToBackStack(fragment?.javaClass?.simpleName)
                commit()
            }
        }
        return true
    }
}
