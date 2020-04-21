package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.FavouritesViewModel

@Module(includes = [ViewModelModule::class])
interface FavouritesModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(FavouritesViewModel::class)
    fun bindFavouritesViewModel(
        favouritesViewModel: FavouritesViewModel
    ): ViewModel
}
