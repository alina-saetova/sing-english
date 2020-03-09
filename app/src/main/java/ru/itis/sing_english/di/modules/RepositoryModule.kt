package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.Provides
import ru.itis.sing_english.data.source.local.SubtitleLocalSource
import ru.itis.sing_english.data.source.remote.SubtitleRemoteSource
import ru.itis.sing_english.data.source.repository.SubtitlesRepository
import javax.inject.Singleton

@Module(includes = [NetModule::class, LocalDataModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSubtitleRepository(localSource: SubtitleLocalSource,
                                  remoteSource: SubtitleRemoteSource): SubtitlesRepository =
        SubtitlesRepository(localSource, remoteSource)
}
