package ru.itis.sing_english.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import retrofit2.HttpException
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.domain.interactors.MainPageInteractor
import javax.inject.Inject

class MainPageViewModel @Inject constructor(
    private val interactor: MainPageInteractor
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private val broadcast = ConflatedBroadcastChannel<String>()

    private var _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    private var _progress = MutableLiveData<LoadingStatus>()
    val progress: LiveData<LoadingStatus>
        get() = _progress

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelJob = viewModelScope.launch {
            try {
                _progress.postValue(LoadingStatus.RUNNING)
                val videos = interactor.getVideos()
                _videos.postValue(videos)
                _progress.postValue(LoadingStatus.SUCCESS)
            }
            catch (e: Exception) {
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
                    _videos.postValue(emptyList())
                    _progress.postValue(LoadingStatus.RUNNING)
                    delay(1000)
                    try {
                        val videos = withContext(Dispatchers.IO) {
                            interactor.searchVideos(it)
                        }
                        _videos.postValue(videos)
                        _progress.postValue(LoadingStatus.SUCCESS)
                    } catch (e: HttpException) {
                        Log.e("EXC_HANDLER", "$e")
                        _progress.postValue(LoadingStatus.FAILED)
                    }
                }
            }
            lastTimeout?.join()
        }
        broadcast.offer(text)
    }

    fun like(video: Video, position: Int) {
        viewModelJob = viewModelScope.launch {
            interactor.addVideo(video)
            _videos.value?.get(position)?.like = true
        }
    }

    fun unlike(video: Video, position: Int) {
        viewModelJob = viewModelScope.launch {
            interactor.deleteVideo(video)
            _videos.value?.get(position)?.like = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
