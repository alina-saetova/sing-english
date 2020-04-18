package ru.itis.sing_english.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.repository.VideoRepository
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(val repository: VideoRepository)
    : ViewModel() {

    private lateinit var viewModelJob: Job

    private var _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    private var _progress = MutableLiveData<LoadingStatus>()
    val progress: LiveData<LoadingStatus>
        get() = _progress

    init {
        loadFavourites()
    }

    fun loadFavourites() {
        viewModelJob = viewModelScope.launch {
            _progress.postValue(LoadingStatus.RUNNING)
            val resp = repository.getFavouriteVideos()
            _videos.postValue(resp)
            _progress.postValue(LoadingStatus.SUCCESS)
        }
    }

    fun unlike(video: Video, position: Int) {
        viewModelJob = viewModelScope.launch {
            repository.deleteVideoFromFavourites(video)
            _videos.value?.get(position)?.like = false
        }
    }

    fun like(video: Video, position: Int) {
        viewModelJob = viewModelScope.launch {
            repository.addVideoToFavourite(video)
            _videos.value?.get(position)?.like = true
        }
    }
}
