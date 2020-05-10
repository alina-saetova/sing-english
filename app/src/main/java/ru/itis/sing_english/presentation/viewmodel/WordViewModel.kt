package ru.itis.sing_english.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.LoadingStatus
import ru.itis.sing_english.domain.interactors.WordDetailInteractor
import javax.inject.Inject

class WordViewModel @Inject constructor(
    private val interactor: WordDetailInteractor
) : ViewModel() {

    private lateinit var viewModelJob: Job

    private var _word = MutableLiveData<DictionaryResponse>()
    val word: LiveData<DictionaryResponse>
        get() = _word

    private var _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    fun loadWord(searchedWord: String) {
        viewModelJob = viewModelScope.launch {
            try {
                _loadingStatus.postValue(LoadingStatus.RUNNING)
                val resp = interactor.getWord(searchedWord)
                if (resp.def.isEmpty()) {
                    _loadingStatus.postValue(LoadingStatus.NOT_FOUND)
                } else {
                    _word.postValue(resp)
                    _loadingStatus.postValue(LoadingStatus.SUCCESS)
                }
            } catch (e: Exception) {
                _loadingStatus.postValue(LoadingStatus.FAILED)
            }
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
