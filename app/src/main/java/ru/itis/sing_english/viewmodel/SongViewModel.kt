package ru.itis.sing_english.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.repository.SubtitleRepository
import javax.inject.Inject

class SongViewModel @Inject constructor(val videoId: String,
                                        val repository: SubtitleRepository
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var _subs = MutableLiveData<List<Subtitle>>()
    val subs: LiveData<List<Subtitle>>
        get() = _subs

    init {
        loadSong(videoId)
    }

    private fun loadSong(videoId: String) {
         viewModelJob = viewModelScope.launch {
             val respSubs = repository.getSubtitles(videoId)
             _subs.postValue(respSubs)
         }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
