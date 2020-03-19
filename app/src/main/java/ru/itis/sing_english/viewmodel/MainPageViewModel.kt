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
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.repository.VideoRepository
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

    init {

    }

    fun search(text: String) {
        viewModelJob = viewModelScope.launch {
            var lastTimeout: Job? = null
            broadcast.consumeEach {
                lastTimeout?.cancel()
                lastTimeout = launch {
                    delay(1000)
                    try {
                        val videos = withContext(Dispatchers.IO) {
                            repository.searchVideos(it)
                        }
                        _videos.postValue(videos)
                    } catch (e: HttpException) {
                        Log.e("EXC_HANDLER", "$e")
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
