package ru.itis.sing_english.di.components

import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.repository.*
import ru.itis.sing_english.di.modules.*
import ru.itis.sing_english.view.ui.MainPageFragment
import ru.itis.sing_english.view.ui.SongFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetModule::class,
    LocalDataModule::class,
    ContextModule::class,
    RepositoryModule::class,
    RemoteSourceModule::class
])
interface AppComponent {

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun injectMainPage(mainPageFragment: MainPageFragment)
    fun injectSongPage(songFragment: SongFragment)

    fun subtitlesDao() : SubtitleDao
    fun wordsDao() : WordDao

    fun subtitleRepository() : SubtitleRepository
    fun wordRepository() : WordRepository
    fun videoRepository() : VideoRepository
}
