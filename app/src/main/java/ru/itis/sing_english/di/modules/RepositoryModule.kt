package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.Provides
import ru.itis.sing_english.data.source.local.SubtitleLocalSource
import ru.itis.sing_english.data.source.local.VideoLocalSource
import ru.itis.sing_english.data.source.local.WordsLocalSource
import ru.itis.sing_english.data.source.remote.SubtitleRemoteSource
import ru.itis.sing_english.data.source.remote.VideoRemoteSource
import ru.itis.sing_english.data.source.remote.WordsRemoteSource
import ru.itis.sing_english.data.source.repository.SubtitlesRepository
import ru.itis.sing_english.data.source.repository.VideoRepository
import ru.itis.sing_english.data.source.repository.WordsRepository
import javax.inject.Singleton

@Module(includes = [NetModule::class, LocalDataModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSubtitleRepository(localSource: SubtitleLocalSource,
                                  remoteSource: SubtitleRemoteSource): SubtitlesRepository =
        SubtitlesRepository(localSource, remoteSource)

    @Provides
    @Singleton
    fun provideWordsRepository(localSource: WordsLocalSource,
                               remoteSource: WordsRemoteSource): WordsRepository =
        WordsRepository(localSource, remoteSource)

    @Provides
    @Singleton
    fun provideVideoRepository(localSource: VideoLocalSource,
                               remoteSource: VideoRemoteSource): VideoRepository =
        VideoRepository(localSource, remoteSource)
}
