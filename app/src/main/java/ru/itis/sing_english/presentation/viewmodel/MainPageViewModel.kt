package ru.itis.sing_english.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.domain.interactors.MainPageInteractor
import ru.itis.sing_english.presentation.view.ui.MainPageFragment.Companion.TYPE_VIDEO_ALL
import ru.itis.sing_english.presentation.view.ui.MainPageFragment.Companion.TYPE_VIDEO_HIPHOP
import ru.itis.sing_english.presentation.view.ui.MainPageFragment.Companion.TYPE_VIDEO_POP
import ru.itis.sing_english.presentation.view.ui.MainPageFragment.Companion.TYPE_VIDEO_ROCK
import ru.itis.sing_english.presentation.view.ui.MainPageFragment.Companion.TYPE_VIDEO_SEARCH
import javax.inject.Inject

class MainPageViewModel @Inject constructor(
    private val interactor: MainPageInteractor
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private val broadcast = ConflatedBroadcastChannel<String>()

    private var _randomVideos = MutableLiveData<List<Video>>()
    val randomVideos: LiveData<List<Video>>
        get() = _randomVideos

    private var _popVideos = MutableLiveData<List<Video>>()
    val popVideos: LiveData<List<Video>>
        get() = _popVideos

    private var _hipHopVideos = MutableLiveData<List<Video>>()
    val hipHopVideos: LiveData<List<Video>>
        get() = _hipHopVideos

    private var _rockVideos = MutableLiveData<List<Video>>()
    val rockVideos: LiveData<List<Video>>
        get() = _rockVideos

    private var _searchVideos = MutableLiveData<List<Video>>()
    val searchVideos: LiveData<List<Video>>
        get() = _searchVideos

    private var _progress = MutableLiveData<LoadingStatus>()
    val progress: LiveData<LoadingStatus>
        get() = _progress

    init {
//        loadVideos()
    }

    private fun loadVideos() {
        viewModelJob = viewModelScope.launch {
            try {
                _progress.postValue(LoadingStatus.RUNNING)
                val videos = interactor.getPopularVideos()
                val popVideos = interactor.getVideosByTopic(TOPIC_POP)
                val hipHopVideos = interactor.getVideosByTopic(TOPIC_HIP_HOP)
                val rockVideos = interactor.getVideosByTopic(TOPIC_ROCK)
                _randomVideos.postValue(videos)
                _popVideos.postValue(popVideos)
                _hipHopVideos.postValue(hipHopVideos)
                _rockVideos.postValue(rockVideos)
                _progress.postValue(LoadingStatus.SUCCESS)
            } catch (e: Exception) {
                _progress.postValue(LoadingStatus.FAILED)
            }
        }
    }

    fun search(text: String) {
        viewModelJob = viewModelScope.launch {
            var lastTimeout: Job? = null
            broadcast.consumeEach {
                lastTimeout?.cancel()
                lastTimeout = launch {
                    _searchVideos.postValue(emptyList())
                    _progress.postValue(LoadingStatus.RUNNING)
                    delay(1000)
                    try {
                        val videos = interactor.searchVideos(it)

                        _searchVideos.postValue(videos)
                        _progress.postValue(LoadingStatus.SUCCESS)
                    } catch (e: HttpException) {
                        _progress.postValue(LoadingStatus.FAILED)
                    }
                }
            }
            lastTimeout?.join()
        }
        broadcast.offer(text)
    }

    fun cleanVideos() {
        _searchVideos.postValue(emptyList())
    }

    fun like(video: Video, position: Int, type: String) {
        viewModelJob = viewModelScope.launch {
            interactor.addVideo(video)
            when (type) {
                TYPE_VIDEO_ALL -> _randomVideos.value?.get(position)?.like = true
                TYPE_VIDEO_POP -> _popVideos.value?.get(position)?.like = true
                TYPE_VIDEO_ROCK -> _rockVideos.value?.get(position)?.like = true
                TYPE_VIDEO_HIPHOP -> _hipHopVideos.value?.get(position)?.like = true
                TYPE_VIDEO_SEARCH -> _searchVideos.value?.get(position)?.like = true
            }
        }
    }

    fun unlike(video: Video, position: Int, type: String) {
        viewModelJob = viewModelScope.launch {
            interactor.deleteVideo(video)
            when (type) {
                TYPE_VIDEO_ALL -> _randomVideos.value?.get(position)?.like = false
                TYPE_VIDEO_POP -> _popVideos.value?.get(position)?.like = false
                TYPE_VIDEO_ROCK -> _rockVideos.value?.get(position)?.like = false
                TYPE_VIDEO_HIPHOP -> _hipHopVideos.value?.get(position)?.like = false
                TYPE_VIDEO_SEARCH -> _searchVideos.value?.get(position)?.like = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    companion object {
        const val TOPIC_POP = "/m/064t9"
        const val TOPIC_HIP_HOP = "/m/0glt670"
        const val TOPIC_ROCK = "/m/06by7"
    }
}
