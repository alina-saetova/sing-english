package ru.itis.sing_english.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.domain.interactors.FavouritesInteractor
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val interactor: FavouritesInteractor
) : ViewModel() {

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
            val resp = interactor.getVideos()
            _videos.postValue(resp)
            _progress.postValue(LoadingStatus.SUCCESS)
        }
    }

    fun unlike(video: Video, position: Int) {
        viewModelJob = viewModelScope.launch {
            interactor.deleteVideo(video)
            _videos.value?.get(position)?.like = false
        }
    }

    fun like(video: Video, position: Int) {
        viewModelJob = viewModelScope.launch {
            interactor.addVideo(video)
            _videos.value?.get(position)?.like = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
