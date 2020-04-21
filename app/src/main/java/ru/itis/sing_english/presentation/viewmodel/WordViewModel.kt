package ru.itis.sing_english.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.domain.interactors.WordDetailInteractor
import javax.inject.Inject

class WordViewModel @Inject constructor(
    private val interactor: WordDetailInteractor
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var _word = MutableLiveData<DictionaryResponse>()
    val word: LiveData<DictionaryResponse>
        get() = _word

//    init {
//        loadWord(searchedWord)
//    }

    fun loadWord(searchedWord: String) {
        viewModelJob = viewModelScope.launch {
            val resp = interactor.getWord(searchedWord)
            _word.postValue(resp)
        }
    }

    fun saveWord() {
        viewModelJob = viewModelScope.launch {
            _word.value?.let { interactor.saveWord(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
