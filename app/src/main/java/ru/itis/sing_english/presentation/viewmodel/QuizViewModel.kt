package ru.itis.sing_english.presentation.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.itis.sing_english.R
import ru.itis.sing_english.data.model.QuestionState
import ru.itis.sing_english.data.model.QuizOption
import ru.itis.sing_english.data.model.State
import ru.itis.sing_english.domain.interactors.QuizInteractor
import javax.inject.Inject

class QuizViewModel @Inject constructor(
    private val interactor: QuizInteractor
) : ViewModel() {

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

    private var _stateQuestion = MutableLiveData<QuestionState>()
    val stateQuestion: LiveData<QuestionState>
        get() = _stateQuestion

    private var _stateManualAnswer = MutableLiveData<State>()
    val stateManualAnswer: LiveData<State>
        get() = _stateManualAnswer

    private var _textInput = MutableLiveData<String>()
    val textInput: LiveData<String>
        get() = _textInput

    init {
        _textInput.value = ""
        _stateQuestion.value = QuestionState.NEXT_QUESTION
        _stateManualAnswer.value = State.DEFAULT
        _clickable.value = true
        loadListWords()
    }

    private fun loadListWords() {
        wordsList = mutableListOf()
        viewModelJob = viewModelScope.launch {
            val list = interactor.getWords()
            var isEnglish = true
            for (word in list) {
                isEnglish = !isEnglish
                wordsList.add(QuizOption(word, isEnglish, State.DEFAULT))
            }
            wordsList.shuffle()
            loadNext()
        }
    }

    private fun loadNext() {
        _currentWord.postValue(wordsList[0])
        indexOfRightAnswer = RANGE_BUTTON.random()
        val listOptions = mutableListOf(_firstOption, _secondOption, _thirdOption, _fourthOption)
        listOptions[indexOfRightAnswer].postValue(wordsList[0])
        listOptions.removeAt(indexOfRightAnswer)

        val w = wordsList.removeAt(0)

        for ((i, opt) in listOptions.withIndex()) {
            opt.postValue(wordsList[i])
        }
        wordsList.add(w)
        wordsList.shuffle()
    }

    fun chooseWay(view: View) {
        when (view.id) {
            R.id.btnManual -> _stateQuestion.value = QuestionState.MANUAL
            R.id.btnMultiple -> _stateQuestion.value = QuestionState.MULTIPLE
        }
    }

    fun answerManual(answer: String) {
        viewModelJob = viewModelScope.launch {
            if (_currentWord.value?.isEnglish == true) {
                if (_currentWord.value?.word?.translation == answer) {
                    _stateManualAnswer.value = State.RIGHT
                    delay(DELAY_FOR_ANSWER)
                    _stateQuestion.value = QuestionState.NEXT_QUESTION
                    _textInput.value = ""
                    _stateManualAnswer.value = State.DEFAULT
                    loadNext()
                } else {
                    _stateManualAnswer.value = State.WRONG
                }
            } else {
                if (_currentWord.value?.word?.text == answer) {
                    _stateManualAnswer.value = State.RIGHT
                    delay(DELAY_FOR_ANSWER)
                    _stateQuestion.value = QuestionState.NEXT_QUESTION
                    _textInput.value = ""
                    _stateManualAnswer.value = State.DEFAULT
                    loadNext()
                } else {
                    _stateManualAnswer.value = State.WRONG
                }
            }
        }
    }

    fun answerBtn(view: View) {
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
                userOption.postValue(userOption.value?.let {
                    QuizOption(
                        it.word,
                        it.isEnglish,
                        State.RIGHT
                    )
                })
            } else {
                userOption.postValue(userOption.value?.let {
                    QuizOption(
                        it.word,
                        it.isEnglish,
                        State.WRONG
                    )
                })
            }
            delay(DELAY_FOR_ANSWER)
            _clickable.postValue(true)
            userOption.postValue(userOption.value?.let {
                QuizOption(
                    it.word,
                    it.isEnglish,
                    State.DEFAULT
                )
            })
            if (goToNext) {
                _stateQuestion.value = QuestionState.NEXT_QUESTION
                loadNext()
            }
        }
    }

    fun showAnswer() {
        if (_currentWord.value?.isEnglish == true) {
            _textInput.value = _currentWord.value?.word?.translation
        } else {
            _textInput.value = _currentWord.value?.word?.text
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        const val DELAY_FOR_ANSWER = 1000L
        val RANGE_BUTTON = (0..3)
    }
}
