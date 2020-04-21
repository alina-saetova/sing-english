package ru.itis.sing_english.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.itis.sing_english.di.App
import ru.itis.sing_english.di.modules.*
import ru.itis.sing_english.di.scope.ApplicationScope
import ru.itis.sing_english.presentation.view.ui.MainActivity

@ApplicationScope
@Component(modules = [
    AppModule::class,
    NetModule::class,
    LocalDataModule::class,
    InteractorModule::class,
    RepositoryModule::class
])
interface AppComponent {

    fun chooseWordsComponentFactory(): ChooseWordsComponent.Factory

    fun favouritesComponentFactory(): FavouritesComponent.Factory

    fun mainPageComponentFactory(): MainPageComponent.Factory

    fun quizComponentFactory(): QuizComponent.Factory

    fun songComponentFactory(): SongComponent.Factory

    fun statisticComponentFactory(): StatisticComponent.Factory

    fun vocabularyComponentFactory(): VocabularyComponent.Factory

    fun wordDetailComponentFactory(): WordDetailComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(mainActivity: MainActivity)
}
