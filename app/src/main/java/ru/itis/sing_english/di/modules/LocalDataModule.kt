package ru.itis.sing_english.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.itis.sing_english.data.local.AppDatabase
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.local.dao.VideoDao
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.model.mapper.SubtitlesMapper
import ru.itis.sing_english.data.model.mapper.VideosMapper
import ru.itis.sing_english.data.model.mapper.WordsMapper
import ru.itis.sing_english.di.scope.ApplicationScope

@Module
class LocalDataModule {

    @Provides
    @ApplicationScope
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(
                app,
                AppDatabase::class.java, "sample.db")
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideSubtitleDao(db: AppDatabase): SubtitleDao = db.subtitleDao()

    @Provides
    @ApplicationScope
    fun provideWordsDao(db: AppDatabase): WordDao = db.wordDao()

    @Provides
    @ApplicationScope
    fun provideVideoDao(db: AppDatabase): VideoDao = db.videoDao()

    @Provides
    @ApplicationScope
    fun provideVideosMapper(): VideosMapper = VideosMapper()

    @Provides
    @ApplicationScope
    fun provideSubtitlesMapper(): SubtitlesMapper = SubtitlesMapper()

    @Provides
    @ApplicationScope
    fun provideWordsMapper(): WordsMapper = WordsMapper()
}
