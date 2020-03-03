package ru.itis.sing_english

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.fragments.FavouritesFragment
import ru.itis.sing_english.fragments.MainPageFragment
import ru.itis.sing_english.fragments.VocabularyFragment

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity(),  BottomNavigationView.OnNavigationItemSelectedListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbar)
        nav_view.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            val fragment = MainPageFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                .commit()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_fav -> {
                supportActionBar?.title = "Favourite"
                supportFragmentManager.also {
                    it.beginTransaction().apply {
                        replace(R.id.container, FavouritesFragment.newInstance())
                        addToBackStack(FavouritesFragment::class.java.simpleName)
                        commit()
                    }
                }
                return true
            }
            R.id.navigation_main -> {
                supportActionBar?.title = "Main"
                supportFragmentManager.also {
                    it.beginTransaction().apply {
                        replace(R.id.container, MainPageFragment.newInstance())
                        addToBackStack(MainPageFragment::class.java.simpleName)
                        commit()
                    }
                }
                return true
            }
            R.id.navigation_vocab -> {
                supportActionBar?.title = "Vocabulary"
                supportFragmentManager.also {
                    it.beginTransaction().apply {
                        replace(R.id.container, VocabularyFragment.newInstance())
                        addToBackStack(VocabularyFragment::class.java.simpleName)
                        commit()
                    }
                }
                return true
            }
        }
        return false
    }
}
