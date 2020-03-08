package ru.itis.sing_english.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.itis.sing_english.data.source.local.AppDatabase
import ru.itis.sing_english.data.source.local.dao.SubtitlesDao
import ru.itis.sing_english.data.source.local.dao.WordsDao
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Module(includes = [ContextModule::class])
class LocalDataModule {

    @Provides
    @Singleton
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "sample.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideSubtitleDao(db: AppDatabase): SubtitlesDao {
        return db.subtitleDao()
    }

    @Provides
    @Singleton
    fun provideWordsDao(db: AppDatabase): WordsDao {
        return db.wordDao()
    }
}