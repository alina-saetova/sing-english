package ru.itis.sing_english.di

import ru.itis.sing_english.di.components.*
import ru.itis.sing_english.presentation.view.ui.MainActivity

object AppInjector {

    lateinit var appComponent: AppComponent
    private var chooseWordsComponent: ChooseWordsComponent? = null
    private var favouritesComponent: FavouritesComponent? = null
    private var mainPageComponent: MainPageComponent? = null
    private var quizComponent: QuizComponent? = null
    private var songComponent: SongComponent? = null
    private var statisticComponent: StatisticComponent? = null
    private var vocabularyComponent: VocabularyComponent? = null
    private var wordDetailComponent: WordDetailComponent? = null

    fun init(app: App) {
        DaggerAppComponent.builder()
            .application(app)
            .build().also { appComponent = it }
            .inject(app)
    }

    fun injectMainActivity(mainActivity: MainActivity) {
        appComponent.inject(mainActivity)
    }

    fun plusChooseWordsComponent(): ChooseWordsComponent =
        chooseWordsComponent ?: appComponent
            .chooseWordsComponentFactory()
            .create()
            .also {
                chooseWordsComponent = it
            }

    fun plusFavouritesComponent(): FavouritesComponent =
        favouritesComponent ?: appComponent
            .favouritesComponentFactory()
            .create()
            .also {
                favouritesComponent = it
            }

    fun plusMainPageComponent(): MainPageComponent =
        mainPageComponent ?: appComponent
            .mainPageComponentFactory()
            .create()
            .also {
                mainPageComponent = it
            }

    fun plusQuizComponent(): QuizComponent =
        quizComponent ?: appComponent
            .quizComponentFactory()
            .create()
            .also {
                quizComponent = it
            }

    fun plusSongComponent(): SongComponent =
        songComponent ?: appComponent
            .songComponentFactory()
            .create()
            .also {
                songComponent = it
            }

    fun plusStatisticComponent(): StatisticComponent =
        statisticComponent ?: appComponent
            .statisticComponentFactory()
            .create()
            .also {
                statisticComponent = it
            }

    fun plusVocabularyComponent(): VocabularyComponent =
        vocabularyComponent ?: appComponent
            .vocabularyComponentFactory()
            .create()
            .also {
                vocabularyComponent = it
            }

    fun plusWordDetailComponent(): WordDetailComponent =
        wordDetailComponent ?: appComponent
            .wordDetailComponentFactory()
            .create()
            .also {
                wordDetailComponent = it
            }

    fun clearChooseWordsComponent() {
        chooseWordsComponent = null
    }

    fun clearFavouritesComponent() {
        favouritesComponent = null
    }

    fun clearMainPageComponent() {
        mainPageComponent = null
    }

    fun clearQuizComponent() {
        quizComponent = null
    }

    fun clearSongComponent() {
        songComponent = null
    }

    fun clearStatisticComponent() {
        statisticComponent = null
    }

    fun clearVocabularyComponent() {
        vocabularyComponent = null
    }

    fun clearWordDetailComponent() {
        wordDetailComponent = null
    }
}
