package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.MainPageViewModel

@Module(includes = [ViewModelModule::class])
interface MainPageModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(MainPageViewModel::class)
    fun bindMainPageViewModel(
        mainPageViewModel: MainPageViewModel
    ): ViewModel
}
