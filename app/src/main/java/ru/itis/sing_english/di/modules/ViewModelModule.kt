package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.viewmodel.*

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindFavouritesViewModel(
        favouritesViewModel: FavouritesViewModel
    ): ViewModel

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(MainPageViewModel::class)
    abstract fun bindMainPageViewModel(
        mainPageViewModel: MainPageViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel::class)
    abstract fun bindQuizViewModel(
        quizViewModel: QuizViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SongViewModel::class)
    abstract fun bindSongViewModel(
        songViewModel: SongViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VocabularyViewModel::class)
    abstract fun bindVocabularyViewModel(
        vocabularyViewModel: VocabularyViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordViewModel::class)
    abstract fun bindWordViewModel(
        wordViewModel: WordViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticViewModel::class)
    abstract fun bindStatisticViewModel(
        statisticViewModel: StatisticViewModel
    ): ViewModel
}
