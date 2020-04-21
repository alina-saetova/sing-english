package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.FavouritesModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.FavouritesFragment

@ScreenScope
@Subcomponent(modules = [FavouritesModule::class])
interface FavouritesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FavouritesComponent
    }

    fun inject(fragment: FavouritesFragment)
}
