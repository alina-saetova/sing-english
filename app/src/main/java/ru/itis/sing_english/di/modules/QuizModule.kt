package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.QuizViewModel

@Module(includes = [ViewModelModule::class])
interface QuizModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(QuizViewModel::class)
    fun bindQuizViewModel(
        quizViewModel: QuizViewModel
    ): ViewModel
}
