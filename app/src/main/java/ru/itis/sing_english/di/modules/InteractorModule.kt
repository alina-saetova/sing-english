package ru.itis.sing_english.di.modules

import dagger.Binds
import dagger.Module
import ru.itis.sing_english.di.scope.ApplicationScope
import ru.itis.sing_english.domain.interactors.*

@Module
interface InteractorModule {

    @Binds
    @ApplicationScope
    fun provideChooseWordInteractor(
        chooseWordInteractorImpl: ChooseWordsInteractorImpl
    ): ChooseWordsInteractor

    @Binds
    @ApplicationScope
    fun provideFavouritesInteractor(
        favouritesInteractorImpl: FavouritesInteractorImpl
    ): FavouritesInteractor

    @Binds
    @ApplicationScope
    fun provideMainPageInteractor(
        mainPageInteractorImpl: MainPageInteractorImpl
    ): MainPageInteractor

    @Binds
    @ApplicationScope
    fun provideQuizInteractor(
        quizInteractorImpl: QuizInteractorImpl
    ): QuizInteractor

    @Binds
    @ApplicationScope
    fun provideSongInteractor(
        songInteractorImpl: SongInteractorImpl
    ): SongInteractor

    @Binds
    @ApplicationScope
    fun provideVocabularyInteractor(
        vocabularyInteractorImpl: VocabularyInteractorImpl
    ): VocabularyInteractor

    @Binds
    @ApplicationScope
    fun provideWordDetailInteractor(
        wordDetailInteractorImpl: WordDetailInteractorImpl
    ): WordDetailInteractor
}
