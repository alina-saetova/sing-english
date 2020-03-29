package ru.itis.sing_english.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.repository.WordRepository
import java.lang.Exception
import javax.inject.Inject

class VocabularyViewModel @Inject constructor(
    val repository: WordRepository
) : ViewModel() {

    private lateinit var viewModelJob: Job

    private var _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words

    private var _progress = MutableLiveData<LoadingStatus>()
    val progress: LiveData<LoadingStatus>
        get() = _progress

    init {
        loadWords()
    }

    fun loadWords() {
        viewModelJob = viewModelScope.launch {
            try {
                _progress.postValue(LoadingStatus.RUNNING)
                val words = repository.getListWords()
                _words.postValue(words)
                _progress.postValue(LoadingStatus.SUCCESS)
            }
            catch (e: Exception) {
                _progress.postValue(LoadingStatus.FAILED)
            }
        }
    }

    fun deleteWord(id: Long) {
        viewModelJob = viewModelScope.launch {
            repository.deleteWord(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
