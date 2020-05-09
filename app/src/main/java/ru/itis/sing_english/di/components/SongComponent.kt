package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.SongModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.SongFragment

@ScreenScope
@Subcomponent(modules = [SongModule::class])
interface SongComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SongComponent
    }

    fun inject(fragment: SongFragment)
}
