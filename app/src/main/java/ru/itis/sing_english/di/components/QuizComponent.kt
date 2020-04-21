package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.QuizModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.QuizFragment

@ScreenScope
@Subcomponent(modules = [QuizModule::class])
interface QuizComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuizComponent
    }

    fun inject(fragment: QuizFragment)
}
