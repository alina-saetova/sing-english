package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.SongModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.Song3RowsFragment
import ru.itis.sing_english.presentation.view.ui.Song5RowsFragment

@ScreenScope
@Subcomponent(modules = [SongModule::class])
interface SongComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SongComponent
    }

    fun inject(fragment: Song3RowsFragment)
    fun inject(fragment: Song5RowsFragment)
}
