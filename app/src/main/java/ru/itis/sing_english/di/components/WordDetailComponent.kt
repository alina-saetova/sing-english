package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.WordDetailModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.WordDetailFragment

@ScreenScope
@Subcomponent(modules = [WordDetailModule::class])
interface WordDetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WordDetailComponent
    }

    fun inject(fragment: WordDetailFragment)
}
