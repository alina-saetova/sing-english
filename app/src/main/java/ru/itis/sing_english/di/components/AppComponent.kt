package ru.itis.sing_english.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.repository.*
import ru.itis.sing_english.di.App
import ru.itis.sing_english.di.modules.*
import ru.itis.sing_english.view.ui.MainPageFragment
import ru.itis.sing_english.view.ui.SongFragment
import javax.inject.Singleton

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [
    NetModule::class,
    LocalDataModule::class,
    RepositoryModule::class,
    RemoteSourceModule::class,
    AndroidInjectionModule::class,
    MainActivityModule::class,
    ViewModelModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)
}
