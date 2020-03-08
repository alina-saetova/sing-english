package ru.itis.sing_english.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(val context: Context) {

    @Singleton
    @Provides
    fun context(): Context = context
}