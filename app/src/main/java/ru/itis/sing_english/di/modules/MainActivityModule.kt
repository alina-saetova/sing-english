package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.MainActivity

@Suppress("unused")
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
