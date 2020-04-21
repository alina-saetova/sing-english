package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.ChooseWordsModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.ChooseWordsFragment

@ScreenScope
@Subcomponent(modules = [ChooseWordsModule::class])
interface ChooseWordsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChooseWordsComponent
    }

    fun inject(fragment: ChooseWordsFragment)
}
