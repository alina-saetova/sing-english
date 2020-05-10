package ru.itis.sing_english.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.domain.interactors.VocabularyInteractor
import javax.inject.Inject

class VocabularyViewModel @Inject constructor(
    private val interactor: VocabularyInteractor
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
                val words = interactor.getWords()
                _words.postValue(words)
                _progress.postValue(LoadingStatus.SUCCESS)
            } catch (e: Exception) {
                _progress.postValue(LoadingStatus.FAILED)
            }
        }
    }

    fun deleteWord(word: Word) {
        viewModelJob = viewModelScope.launch {
            interactor.deleteWord(word)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
