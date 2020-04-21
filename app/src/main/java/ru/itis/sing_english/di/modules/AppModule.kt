package ru.itis.sing_english.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.itis.sing_english.di.scope.ApplicationScope

@Module
class AppModule {

    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context = application.applicationContext
}
