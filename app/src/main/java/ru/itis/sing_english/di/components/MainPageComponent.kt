package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.MainPageModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.MainPageFragment

@ScreenScope
@Subcomponent(modules = [MainPageModule::class])
interface MainPageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainPageComponent
    }

    fun inject(fragment: MainPageFragment)
}
