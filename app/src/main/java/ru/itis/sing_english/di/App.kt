package ru.itis.sing_english.di

import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class App: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun androidInjector(): DispatchingAndroidInjector<Any> = dispatchingAndroidInjector
}
