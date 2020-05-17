package ru.itis.sing_english.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.sing_english.CoroutinesTestExtension
import ru.itis.sing_english.InstantExecutorExtension
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.domain.interactors.VocabularyInteractor

@ExtendWith(InstantExecutorExtension::class, MockKExtension::class)
class VocabularyViewModelTest {

    @MockK
    lateinit var mockInteractor: VocabularyInteractor

    lateinit var viewModel: VocabularyViewModel

    @MockK
    lateinit var observerProgress: Observer<LoadingStatus>

    @MockK
    lateinit var observerWords: Observer<List<Word>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val expectedWords = mutableListOf(Word(2, "text", "translation"))

    @BeforeEach
    fun setUp() {
        coEvery { mockInteractor.getWords() } returns expectedWords
        viewModel = spyk(VocabularyViewModel(mockInteractor))
    }

    @Test
    fun loadWordsSuccess() {
        every { observerProgress.onChanged(any()) } just runs
        every { observerWords.onChanged(any()) } just runs
        viewModel.words.observeForever(observerWords)
        viewModel.progress.observeForever(observerProgress)

        viewModel.loadWords()

        coVerify {
            observerProgress.onChanged(LoadingStatus.RUNNING)
            mockInteractor.getWords()
            observerWords.onChanged(expectedWords)
            observerProgress.onChanged(LoadingStatus.SUCCESS)
        }
        assertEquals(expectedWords, viewModel.words.value)
    }

    @Test
    fun loadWordsFailed() {
        coEvery { mockInteractor.getWords() } throws Exception()
        viewModel = spyk(VocabularyViewModel(mockInteractor))
        every { observerProgress.onChanged(any()) } just runs
        viewModel.progress.observeForever(observerProgress)

        viewModel.loadWords()

        coVerify {
            observerProgress.onChanged(LoadingStatus.RUNNING)
            mockInteractor.getWords()
            observerProgress.onChanged(LoadingStatus.FAILED)
        }
        viewModel.words.value?.isEmpty()?.let { assertTrue(it) }
    }
}
