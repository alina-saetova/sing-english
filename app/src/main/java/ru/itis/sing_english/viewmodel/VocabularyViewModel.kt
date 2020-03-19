package ru.itis.sing_english.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.repository.WordRepository
import javax.inject.Inject

class VocabularyViewModel @Inject constructor(
    val repository: WordRepository
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelJob = viewModelScope.launch {
            val words = repository.getListWords()
            _words.postValue(words)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun deleteWord(id: Long) {
        viewModelJob = viewModelScope.launch {
            repository.deleteWord(id)
        }
    }
}
