package ru.itis.sing_english.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.spyk
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.sing_english.CoroutinesTestExtension
import ru.itis.sing_english.InstantExecutorExtension
import ru.itis.sing_english.data.model.DifficultyLevel
import ru.itis.sing_english.data.model.SongRow

@ExtendWith(InstantExecutorExtension::class, MockKExtension::class)
class StatisticViewModelTest {

    lateinit var viewModel: StatisticViewModel

    @MockK
    lateinit var observerNumOfCorrectAns: Observer<Int>

    @MockK
    lateinit var observerStatistic: Observer<List<SongRow>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val initialLyric = mutableListOf(
        SongRow("empty", mutableListOf(), false),
        SongRow("empty", mutableListOf(), false),
        SongRow(
            "I WANT A BOYFRIEND", mutableListOf(42, 3), true,
            DifficultyLevel.MEDIUM, mutableListOf()
        ),
        SongRow(
            "AGAIN AND BOYFRIEND", mutableListOf(42, 2), true,
            DifficultyLevel.MEDIUM, mutableListOf()
        ),
        SongRow(
            "BUT I JUST KEEP HITTING DEAD ENDS", mutableListOf(42, 5), true,
            DifficultyLevel.MEDIUM, mutableListOf()
        )
    )

    private val initialAnswers = mutableListOf("BOYFRIEND", "AGAIN", "DEAD")

    @BeforeEach
    fun setUp() {
        viewModel = spyk(StatisticViewModel())
        every { observerNumOfCorrectAns.onChanged(any()) } just runs
        every { observerStatistic.onChanged(any()) } just runs
        viewModel.numOfCorrectAnswers.observeForever(observerNumOfCorrectAns)
        viewModel.statistic.observeForever(observerStatistic)
    }

    @Test
    fun checkCorrectnessArray() {
        val expectedValue = mutableListOf(true, false, true)
        viewModel.loadStatistic(initialLyric, initialAnswers)

        val actualValue = viewModel.statistic.value?.get(0)?.isCorrect
        viewModel.statistic.value?.get(1)?.isCorrect?.let { actualValue?.addAll(it) }
        viewModel.statistic.value?.get(2)?.isCorrect?.let { actualValue?.addAll(it) }

        Assertions.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun checkNumOfCorrectAnswers() {
        val expectedValue = 2
        viewModel.loadStatistic(initialLyric, initialAnswers)

        Assertions.assertEquals(expectedValue, viewModel.numOfCorrectAnswers.value)
    }
}
