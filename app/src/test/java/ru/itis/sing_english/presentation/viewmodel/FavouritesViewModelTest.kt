package ru.itis.sing_english.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.sing_english.CoroutinesTestExtension
import ru.itis.sing_english.InstantExecutorExtension
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.domain.interactors.FavouritesInteractor

@ExtendWith(InstantExecutorExtension::class, MockKExtension::class)
class FavouritesViewModelTest {

    @MockK
    lateinit var mockInteractor: FavouritesInteractor

    lateinit var viewModel: FavouritesViewModel

    @MockK
    lateinit var observerProgress: Observer<LoadingStatus>

    @MockK
    lateinit var observerVideos: Observer<List<Video>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val expectedVideos = mutableListOf(
        Video(
            2, "videoId", "imgUrl",
            "title", "channelTitle", true
        )
    )

    @BeforeEach
    fun setUp() {
        coEvery { mockInteractor.getVideos() } returns expectedVideos
        viewModel = spyk(FavouritesViewModel(mockInteractor))
    }

    @Test
    fun loadFavourites() {
        every { observerProgress.onChanged(any()) } just runs
        every { observerVideos.onChanged(any()) } just runs
        viewModel.videos.observeForever(observerVideos)
        viewModel.progress.observeForever(observerProgress)

        viewModel.loadFavourites()

        coVerifyOrder {
            observerProgress.onChanged(LoadingStatus.RUNNING)
            mockInteractor.getVideos()
            observerVideos.onChanged(expectedVideos)
            observerProgress.onChanged(LoadingStatus.SUCCESS)
        }
        assertEquals(expectedVideos, viewModel.videos.value)
    }

    @Test
    fun like() {
        coEvery { mockInteractor.addVideo(any()) } just runs

        viewModel.like(expectedVideos[0], 0)

        coVerify {
            mockInteractor.addVideo(expectedVideos[0])
        }
        viewModel.videos.value?.get(0)?.like?.let { assertTrue(it) }
    }

    @Test
    fun unlike() {
        coEvery { mockInteractor.deleteVideo(any()) } just runs

        viewModel.unlike(expectedVideos[0], 0)

        coVerify {
            mockInteractor.deleteVideo(expectedVideos[0])
        }
        viewModel.videos.value?.get(0)?.like?.let { assertFalse(it) }
    }
}
