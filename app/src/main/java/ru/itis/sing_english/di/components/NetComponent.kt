package ru.itis.sing_english.di.components

import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.di.modules.NetModule
import ru.itis.sing_english.view.ui.MainPageFragment
import ru.itis.sing_english.view.ui.SongFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface NetComponent {

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun injectMainPage(mainPageFragment: MainPageFragment)
    fun injectSongPage(songFragment: SongFragment)
}
