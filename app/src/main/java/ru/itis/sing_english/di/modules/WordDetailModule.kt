package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.WordViewModel

@Module(includes = [ViewModelModule::class])
interface WordDetailModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(WordViewModel::class)
    fun bindWordViewModel(
        wordViewModel: WordViewModel
    ): ViewModel
}
