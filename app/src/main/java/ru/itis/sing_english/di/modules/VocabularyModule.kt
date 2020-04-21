package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.VocabularyViewModel

@Module(includes = [ViewModelModule::class])
interface VocabularyModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(VocabularyViewModel::class)
    fun bindVocabularyViewModel(
        vocabularyViewModel: VocabularyViewModel
    ): ViewModel
}
