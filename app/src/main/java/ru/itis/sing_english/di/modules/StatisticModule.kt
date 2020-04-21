package ru.itis.sing_english.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.sing_english.di.ViewModelKey
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.viewmodel.StatisticViewModel

@Module(includes = [ViewModelModule::class])
interface StatisticModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(StatisticViewModel::class)
    fun bindStatisticViewModel(
        statisticViewModel: StatisticViewModel
    ): ViewModel
}
