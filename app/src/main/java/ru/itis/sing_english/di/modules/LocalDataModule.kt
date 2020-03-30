package ru.itis.sing_english.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.itis.sing_english.data.local.AppDatabase
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.local.dao.VideoDao
import ru.itis.sing_english.data.local.dao.WordDao
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(
                app,
                AppDatabase::class.java, "sample.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideSubtitleDao(db: AppDatabase): SubtitleDao = db.subtitleDao()

    @Provides
    @Singleton
    fun provideWordsDao(db: AppDatabase): WordDao = db.wordDao()

    @Provides
    @Singleton
    fun provideVideoDao(db: AppDatabase): VideoDao = db.videoDao()
}
