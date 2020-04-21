package ru.itis.sing_english.di.components

import dagger.Subcomponent
import ru.itis.sing_english.di.modules.StatisticModule
import ru.itis.sing_english.di.scope.ScreenScope
import ru.itis.sing_english.presentation.view.ui.StatisticFragment

@ScreenScope
@Subcomponent(modules = [StatisticModule::class])
interface StatisticComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StatisticComponent
    }

    fun inject(fragment: StatisticFragment)
}
