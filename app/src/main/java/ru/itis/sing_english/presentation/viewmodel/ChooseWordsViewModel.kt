package ru.itis.sing_english.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.WordGrid
import ru.itis.sing_english.domain.interactors.ChooseWordsInteractor
import javax.inject.Inject

class ChooseWordsViewModel @Inject constructor(
    private val interactor: ChooseWordsInteractor
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var savedWords = mutableListOf<String>()

    private var _words = MutableLiveData<MutableList<WordGrid>>()
    val words: LiveData<MutableList<WordGrid>>
        get() = _words

    fun loadWords(list: MutableList<WordGrid>) {
        val i = list.listIterator()
        while (i.hasNext()) {
            var word = i.next()
            if (word.text.contains(COMMA_REGEX)) {
                word = WordGrid(word.text.substring(0, word.text.length - 1), word.flag)
                i.set(word)
            }
            if (!word.text.matches(LETTERS_REGEX.toRegex())) {
                i.remove()
            }
        }
        _words.value = list
    }

    fun saveWord(word: String, position: Int) {
        viewModelJob = viewModelScope.launch {
            if (!savedWords.contains(word)) {
                savedWords.add(word)
                interactor.saveWord(word)

                _words.value?.get(position)?.flag =  true
            }
            else {
                savedWords.remove(word)
                interactor.deleteWord(word)

                _words.value?.get(position)?.flag = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        const val COMMA_REGEX = ","
        const val LETTERS_REGEX = "[a-zA-Z]+"
    }
}
