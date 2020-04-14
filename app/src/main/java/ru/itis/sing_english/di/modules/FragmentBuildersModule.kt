package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.itis.sing_english.view.ui.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFavouritesFragment(): FavouritesFragment

    @ContributesAndroidInjector
    abstract fun contributeMainPageFragment(): MainPageFragment

    @ContributesAndroidInjector
    abstract fun contributeQuizFragment(): QuizFragment

    @ContributesAndroidInjector
    abstract fun contributeSong5RowsFragment(): Song5RowsFragment

    @ContributesAndroidInjector
    abstract fun contributeSong3RowsFragment(): Song3RowsFragment

    @ContributesAndroidInjector
    abstract fun contributeMainVocabularyFragment(): VocabularyFragment

    @ContributesAndroidInjector
    abstract fun contributeWordDetailFragment(): WordDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeChooseLevelFragment(): ChooseLevelFragment
}
