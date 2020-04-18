package ru.itis.sing_english.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.WordGrid
import ru.itis.sing_english.data.repository.WordRepository
import javax.inject.Inject

class ChooseWordsViewModel @Inject constructor(
    val repository: WordRepository
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var savedWords = mutableListOf<String>()

    private var _words = MutableLiveData<MutableList<WordGrid>>()
    val words: LiveData<MutableList<WordGrid>>
        get() = _words

    fun loadWords(list: List<WordGrid>) {
        _words.value = list.toMutableList()
    }

    fun saveWord(word: String, position: Int) {
        viewModelJob = viewModelScope.launch {
            if (!savedWords.contains(word)) {
                savedWords.add(word)
                repository.saveWord(word)

                _words.value?.get(position)?.flag =  true
            }
            else {
                savedWords.remove(word)
                repository.deleteWord(word.toLowerCase())

                _words.value?.get(position)?.flag = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
