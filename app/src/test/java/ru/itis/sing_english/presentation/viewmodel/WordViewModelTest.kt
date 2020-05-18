package ru.itis.sing_english.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.sing_english.CoroutinesTestExtension
import ru.itis.sing_english.InstantExecutorExtension
import ru.itis.sing_english.data.model.Def
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.domain.interactors.WordDetailInteractor

@ExtendWith(InstantExecutorExtension::class, MockKExtension::class)
class WordViewModelTest {

    @MockK
    lateinit var mockInteractor: WordDetailInteractor

    lateinit var viewModel: WordViewModel

    @MockK
    lateinit var observerLoading: Observer<LoadingStatus>

    @MockK
    lateinit var observerDictResp: Observer<DictionaryResponse>

    @MockK
    lateinit var observerButtonState: Observer<Boolean>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val emptyResponse = DictionaryResponse(mutableListOf())
    private val fullResponse = DictionaryResponse(
        mutableListOf(
            Def(
                "word", "pos", "transcr", mutableListOf()
            )
        )
    )
    private val searchedWord = "word"

    @Test
    fun loadWordWithEmptyResponse() {
        coEvery { mockInteractor.getWord(any()) } returns emptyResponse
        viewModel = spyk(WordViewModel(mockInteractor))
        every { observerLoading.onChanged(any()) } just runs
        every { observerButtonState.onChanged(any()) } just runs
        viewModel.loadingStatus.observeForever(observerLoading)
        viewModel.buttonSaveState.observeForever(observerButtonState)

        viewModel.loadWord(searchedWord, false)

        coVerify {
            observerButtonState.onChanged(false)
            observerLoading.onChanged(LoadingStatus.RUNNING)
            mockInteractor.getWord(searchedWord)
            observerLoading.onChanged(LoadingStatus.NOT_FOUND)
        }
    }

    @Test
    fun loadWordError() {
        coEvery { mockInteractor.getWord(any()) } throws Exception()
        viewModel = spyk(WordViewModel(mockInteractor))
        every { observerLoading.onChanged(any()) } just runs
        every { observerButtonState.onChanged(any()) } just runs
        viewModel.loadingStatus.observeForever(observerLoading)
        viewModel.buttonSaveState.observeForever(observerButtonState)

        viewModel.loadWord(searchedWord, true)

        coVerify {
            observerLoading.onChanged(LoadingStatus.RUNNING)
            mockInteractor.getWord(searchedWord)
            observerLoading.onChanged(LoadingStatus.FAILED)
        }
    }

    @Test
    fun loadWordSuccess() {
        coEvery { mockInteractor.getWord(any()) } returns fullResponse
        viewModel = spyk(WordViewModel(mockInteractor))
        every { observerLoading.onChanged(any()) } just runs
        every { observerButtonState.onChanged(any()) } just runs
        every { observerDictResp.onChanged(any()) } just runs
        viewModel.loadingStatus.observeForever(observerLoading)
        viewModel.buttonSaveState.observeForever(observerButtonState)
        viewModel.word.observeForever(observerDictResp)

        viewModel.loadWord(searchedWord, true)

        coVerify {
            observerButtonState.onChanged(true)
            observerLoading.onChanged(LoadingStatus.RUNNING)
            mockInteractor.getWord(searchedWord)
            observerDictResp.onChanged(fullResponse)
            observerLoading.onChanged(LoadingStatus.SUCCESS)
        }

        assertEquals(fullResponse.def[0].word, viewModel.word.value?.def?.get(0)?.word)
    }
}
