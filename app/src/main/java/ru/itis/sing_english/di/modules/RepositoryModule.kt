package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.Provides
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.local.dao.VideoDao
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.services.SubtitleService
import ru.itis.sing_english.data.services.WordService
import ru.itis.sing_english.data.services.YoutubeVideoService
import ru.itis.sing_english.data.repository.SubtitlesRepositoryImpl
import ru.itis.sing_english.data.repository.VideoRepositoryImpl
import ru.itis.sing_english.data.repository.WordRepositoryImpl
import javax.inject.Singleton

@Module(includes = [NetModule::class, LocalDataModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSubtitleRepository(api: SubtitleService,
                                  dao: SubtitleDao): SubtitlesRepositoryImpl =
        SubtitlesRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideWordsRepository(api: WordService,
                               dao: WordDao): WordRepositoryImpl =
        WordRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideVideoRepository(api: YoutubeVideoService,
                               dao: VideoDao): VideoRepositoryImpl =
        VideoRepositoryImpl(api, dao)
}
