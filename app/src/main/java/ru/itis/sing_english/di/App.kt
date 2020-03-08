package ru.itis.sing_english.di

import android.app.Application
import ru.itis.sing_english.di.components.AppComponent
import ru.itis.sing_english.di.components.DaggerAppComponent
import ru.itis.sing_english.di.modules.ContextModule


class App: Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().contextModule(ContextModule(this)).build()
    }
}
