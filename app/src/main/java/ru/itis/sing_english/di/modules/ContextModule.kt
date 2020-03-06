package ru.itis.sing_english.di.modules

import android.app.Application
import android.content.Context
import dagger.Module

import dagger.Provides
import javax.inject.Singleton


@Module
class ContextModule(context: Context) {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context =
        application.applicationContext
}