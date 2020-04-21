package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.VocabularyModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.VocabularyFragment

@ScreenScope
@Subcomponent(modules = [VocabularyModule::class])
interface VocabularyComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): VocabularyComponent
    }

    fun inject(fragment: VocabularyFragment)
}
