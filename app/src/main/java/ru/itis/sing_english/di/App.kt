package ru.itis.sing_english.di

import android.app.Application
import ru.itis.sing_english.view.components.DaggerNetComponent
import ru.itis.sing_english.di.components.NetComponent


class App: Application() {

    companion object {
        lateinit var component: NetComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerNetComponent.create()
    }
}
