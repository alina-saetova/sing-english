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
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.domain.interactors.MainPageInteractor
import ru.itis.sing_english.presentation.view.ui.MainPageFragment.Companion.TYPE_VIDEO_POP
import ru.itis.sing_english.presentation.viewmodel.MainPageViewModel.Companion.TOPIC_HIP_HOP
import ru.itis.sing_english.presentation.viewmodel.MainPageViewModel.Companion.TOPIC_POP
import ru.itis.sing_english.presentation.viewmodel.MainPageViewModel.Companion.TOPIC_ROCK

@ExtendWith(InstantExecutorExtension::class, MockKExtension::class)
class MainPageViewModelTest {

    @MockK
    lateinit var mockInteractor: MainPageInteractor

    lateinit var viewModel: MainPageViewModel

    @MockK
    lateinit var observerRandomVideos: Observer<List<Video>>

    @MockK
    lateinit var observerPopVideos: Observer<List<Video>>

    @MockK
    lateinit var observerHiphopVideos: Observer<List<Video>>

    @MockK
    lateinit var observerRockVideos: Observer<List<Video>>

    @MockK
    lateinit var observerSearchVideos: Observer<List<Video>>

    @MockK
    lateinit var observerProgressBar: Observer<LoadingStatus>

    @MockK
    lateinit var observerSearchLoading: Observer<LoadingStatus>

    @MockK
    lateinit var observerMainLoading: Observer<LoadingStatus>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val expectedVideos = mutableListOf(Video(2, "videoId", "imgUrl",
        "title", "channelTitle", true))

    @Test
    fun loadVideosSuccess() {
        coEvery { mockInteractor.getPopularVideos() } returns expectedVideos
        coEvery { mockInteractor.getVideosByTopic(any()) } returns expectedVideos
        viewModel = spyk(MainPageViewModel(mockInteractor))
        observeLivedata()

        viewModel.loadVideos()

        coVerify {
            observerMainLoading.onChanged(LoadingStatus.RUNNING)
            observerProgressBar.onChanged(LoadingStatus.RUNNING)
            mockInteractor.apply {
                getPopularVideos()
                getVideosByTopic(TOPIC_POP)
                getVideosByTopic(TOPIC_HIP_HOP)
                getVideosByTopic(TOPIC_ROCK)
            }
            observerRandomVideos.onChanged(expectedVideos)
            observerPopVideos.onChanged(expectedVideos)
            observerHiphopVideos.onChanged(expectedVideos)
            observerRockVideos.onChanged(expectedVideos)
            observerMainLoading.onChanged(LoadingStatus.SUCCESS)
            observerProgressBar.onChanged(LoadingStatus.SUCCESS)
        }
    }

    @Test
    fun like() {
        coEvery { mockInteractor.getPopularVideos() } returns expectedVideos
        coEvery { mockInteractor.getVideosByTopic(any()) } returns expectedVideos
        coEvery { mockInteractor.addVideo(any()) } just runs
        viewModel = spyk(MainPageViewModel(mockInteractor))
        observeLivedata()

        viewModel.loadVideos()
        viewModel.like(expectedVideos[0], 0, TYPE_VIDEO_POP)

        coVerify {
            mockInteractor.addVideo(expectedVideos[0])
        }
        viewModel.popVideos.value?.get(0)?.like?.let { assertTrue(it) }
    }

    @Test
    fun unlike() {
        coEvery { mockInteractor.getPopularVideos() } returns expectedVideos
        coEvery { mockInteractor.getVideosByTopic(any()) } returns expectedVideos
        coEvery { mockInteractor.deleteVideo(any()) } just runs
        viewModel = spyk(MainPageViewModel(mockInteractor))
        observeLivedata()

        viewModel.loadVideos()
        viewModel.unlike(expectedVideos[0], 0, TYPE_VIDEO_POP)

        coVerify {
            mockInteractor.deleteVideo(expectedVideos[0])
        }
        viewModel.popVideos.value?.get(0)?.like?.let { assertFalse(it) }
    }

    private fun observeLivedata() {
        every { observerRandomVideos.onChanged(any()) } just runs
        every { observerPopVideos.onChanged(any()) } just runs
        every { observerHiphopVideos.onChanged(any()) } just runs
        every { observerRockVideos.onChanged(any()) } just runs
        every { observerSearchVideos.onChanged(any()) } just runs
        every { observerProgressBar.onChanged(any()) } just runs
        every { observerMainLoading.onChanged(any()) } just runs
        every { observerSearchLoading.onChanged(any()) } just runs
        viewModel.apply {
            randomVideos.observeForever(observerRandomVideos)
            popVideos.observeForever(observerPopVideos)
            hipHopVideos.observeForever(observerHiphopVideos)
            rockVideos.observeForever(observerRockVideos)
            searchVideos.observeForever(observerSearchVideos)
            progressBar.observeForever(observerProgressBar)
            mainLoadingStatus.observeForever(observerMainLoading)
            searchLoadingStatus.observeForever(observerSearchLoading)
        }
    }
}
