package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.SongViewModel

@Module(includes = [ViewModelModule::class])
interface SongModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(SongViewModel::class)
    fun bindSongViewModel(
        songViewModel: SongViewModel
    ): ViewModel
}
