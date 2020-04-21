package ru.itis.sing_english.di.modules

import dagger.Binds
import dagger.Module
import ru.itis.sing_english.data.repository.*
import ru.itis.sing_english.di.scope.ApplicationScope

@Module
interface RepositoryModule {

    @Binds
    @ApplicationScope
    fun bindVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository

    @Binds
    @ApplicationScope
    fun bindWordRepository(wordRepositoryImpl: WordRepositoryImpl): WordRepository

    @Binds
    @ApplicationScope
    fun bindSubtitleRepository(subtitlesRepositoryImpl: SubtitlesRepositoryImpl): SubtitleRepository
}
