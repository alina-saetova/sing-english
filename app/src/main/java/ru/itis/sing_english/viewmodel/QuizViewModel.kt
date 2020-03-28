package ru.itis.sing_english.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.QuizOption
import ru.itis.sing_english.data.model.State
import ru.itis.sing_english.data.repository.WordRepository
import javax.inject.Inject

class QuizViewModel @Inject constructor(val repository: WordRepository) : ViewModel() {

    private lateinit var viewModelJob: Job
    private lateinit var wordsList: MutableList<QuizOption>
    private var indexOfRightAnswer = 0

    private var _currentWord = MutableLiveData<QuizOption>()
    val currentWord: LiveData<QuizOption>
        get() = _currentWord

    private var _firstOption = MutableLiveData<QuizOption>()
    val firstOption: LiveData<QuizOption>
        get() = _firstOption

    private var _secondOption = MutableLiveData<QuizOption>()
    val secondOption: LiveData<QuizOption>
        get() = _secondOption

    private var _thirdOption = MutableLiveData<QuizOption>()
    val thirdOption: LiveData<QuizOption>
        get() = _thirdOption

    private var _fourthOption = MutableLiveData<QuizOption>()
    val fourthOption: LiveData<QuizOption>
        get() = _fourthOption

    private var _clickable = MutableLiveData<Boolean>()
    val clickable: LiveData<Boolean>
        get() = _clickable

    init {
        _clickable.value = true
        loadListWords()
    }

    private fun loadListWords() {
        wordsList = mutableListOf()
        viewModelJob = viewModelScope.launch {
            val list = repository.getListWords()
            for (word in list) {
                wordsList.add(QuizOption(word, State.DEFAULT))
            }
            wordsList.shuffle()
            loadNext()
        }
    }

    private fun loadNext() {
        _currentWord.postValue(wordsList[0])
        indexOfRightAnswer = (0..3).random()
        val listOptions = mutableListOf(_firstOption, _secondOption, _thirdOption, _fourthOption)
        listOptions[indexOfRightAnswer].postValue(wordsList[0])
        listOptions.removeAt(indexOfRightAnswer)

        val w = wordsList.removeAt(0)

        for ((i, opt) in listOptions.withIndex()) {
            opt.postValue(wordsList[i])
        }
        wordsList.add(w)
    }

    fun answer(view: View) {
        _clickable.value = false
        var userOption = _currentWord
        when (view.id) {
            R.id.btn_first -> userOption = _firstOption
            R.id.btn_second -> userOption = _secondOption
            R.id.btn_third -> userOption = _thirdOption
            R.id.btn_fourth -> userOption = _fourthOption
        }
        var goToNext = false
        viewModelScope.launch {
            if (userOption.value?.word?.text == _currentWord.value?.word?.text) {
                goToNext = true
                userOption.postValue(userOption.value?.word?.let { QuizOption(it, State.RIGHT) })
            }
            else {
                userOption.postValue(userOption.value?.word?.let { QuizOption(it, State.WRONG) })
            }
            delay(2000)
            _clickable.postValue(true)
            userOption.postValue(userOption.value?.word?.let { QuizOption(it, State.DEFAULT) })
            if (goToNext) {
                loadNext()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
