package ru.itis.sing_english.di.components

import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.data.source.local.dao.SubtitlesDao
import ru.itis.sing_english.data.source.local.dao.WordsDao
import ru.itis.sing_english.di.modules.ContextModule
import ru.itis.sing_english.di.modules.LocalDataModule
import ru.itis.sing_english.di.modules.NetModule
import ru.itis.sing_english.view.ui.MainPageFragment
import ru.itis.sing_english.view.ui.SongFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, LocalDataModule::class, ContextModule::class])
interface AppComponent {

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun injectMainPage(mainPageFragment: MainPageFragment)
    fun injectSongPage(songFragment: SongFragment)

    fun getSubtitlesDao() : SubtitlesDao
    fun getWordsDao() : WordsDao
}
