package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.ChooseWordsViewModel

@Module(includes = [ViewModelModule::class])
interface ChooseWordsModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(ChooseWordsViewModel::class)
    fun bindChooseWordsViewModel(
        chooseWordsViewModel: ChooseWordsViewModel
    ): ViewModel
}
