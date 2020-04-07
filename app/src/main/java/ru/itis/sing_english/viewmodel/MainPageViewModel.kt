package ru.itis.sing_english.viewmodel

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
import ru.itis.sing_english.data.repository.VideoRepository
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainPageViewModel @Inject constructor(val repository: VideoRepository)
    : ViewModel(), CoroutineScope by MainScope() {

    private lateinit var viewModelJob: Job
    private val broadcast = ConflatedBroadcastChannel<String>()

    private var _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    private var _progress = MutableLiveData<LoadingStatus>()
    val progress: LiveData<LoadingStatus>
        get() = _progress

    init {
        load()
    }

    private fun load() {
        viewModelJob = viewModelScope.launch {
            try {
                _progress.postValue(LoadingStatus.RUNNING)
                val videos = repository.getPopularVideos()
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
                            repository.searchVideos(it)
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

    fun like(video: Video) {
        viewModelJob = viewModelScope.launch {
            repository.addVideoToFavourite(video)
        }
    }

    fun unlike(video: Video) {
        viewModelJob = viewModelScope.launch {
            repository.deleteVideoFromFavourites(video)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
