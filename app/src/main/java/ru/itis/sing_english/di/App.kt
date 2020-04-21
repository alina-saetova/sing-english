package ru.itis.sing_english.di

import android.app.Application


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}
