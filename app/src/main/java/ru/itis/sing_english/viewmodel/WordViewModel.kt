package ru.itis.sing_english.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.repository.WordRepository
import javax.inject.Inject

class WordViewModel @Inject constructor(val searchedWord: String,
                                        val repository: WordRepository)
    : ViewModel() {

    private lateinit var viewModelJob: Job
    private var _word = MutableLiveData<DictionaryResponse>()
    val word: LiveData<DictionaryResponse>
        get() = _word

    init {
        loadWord(searchedWord)
    }

    private fun loadWord(searchedWord: String) {
        viewModelJob = viewModelScope.launch {
            val resp = repository.getWord(searchedWord)
            _word.postValue(resp)
        }
    }

    fun saveWord() {
        viewModelJob = viewModelScope.launch {
            _word.value?.let { repository.saveWord(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
