package ru.itis.sing_english.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.sing_english.CoroutinesTestExtension
import ru.itis.sing_english.InstantExecutorExtension
import ru.itis.sing_english.data.model.WordGrid
import ru.itis.sing_english.domain.interactors.ChooseWordsInteractor

@ExtendWith(InstantExecutorExtension::class, MockKExtension::class)
class ChooseWordsViewModelTest {

    @MockK
    lateinit var mockInteractor: ChooseWordsInteractor

    lateinit var viewModel: ChooseWordsViewModel

    @MockK
    lateinit var observerWords: Observer<MutableList<WordGrid>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val initialWords = mutableListOf(
        WordGrid("comma,", true),
        WordGrid("num11bers22", false),
        WordGrid("ch-ars)", false),
        WordGrid("normal", false)
    )

    @BeforeEach
    fun setUp() {
        coEvery { mockInteractor.saveWord(any()) } just runs
        coEvery { mockInteractor.deleteWord(any()) } just runs
        viewModel = spyk(ChooseWordsViewModel(mockInteractor))

        every { observerWords.onChanged(any()) } just runs
        viewModel.words.observeForever(observerWords)
    }

    @Test
    fun loadWords() {
        val expectedWords = mutableListOf(
            WordGrid("comma", true),
            WordGrid("normal", false)
        )

        viewModel.loadWords(initialWords)

        assertEquals(expectedWords, viewModel.words.value)
    }

    @Test
    fun saveWordWhenItsNotSaved() {
        viewModel.loadWords(initialWords)
        viewModel.saveWord(initialWords[0].text, 0)

        coVerify {
            mockInteractor.saveWord(initialWords[0].text)
        }
        viewModel.words.value?.get(0)?.flag?.let { assertTrue(it) }
    }

    @Test
    fun saveWordWhenItsAlreadySaved() {
        viewModel.loadWords(initialWords)
        viewModel.saveWord(initialWords[0].text, 0)
        viewModel.saveWord(initialWords[0].text, 0)

        coVerifyOrder {
            mockInteractor.saveWord(initialWords[0].text)
            mockInteractor.deleteWord(initialWords[0].text)
        }
        viewModel.words.value?.get(0)?.flag?.let { assertFalse(it) }
    }
}
