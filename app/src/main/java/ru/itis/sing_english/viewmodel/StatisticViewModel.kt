package ru.itis.sing_english.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.itis.sing_english.data.model.SongRow
import javax.inject.Inject

class StatisticViewModel @Inject constructor() : ViewModel() {

    private var _statistic = MutableLiveData<List<SongRow>>()
    val statistic: LiveData<List<SongRow>>
        get() = _statistic

    private var _numOfCorrectAnswers = MutableLiveData<Int>()
    val numOfCorrectAnswers: LiveData<Int>
        get() = _numOfCorrectAnswers

    fun loadStatistic(lyric: List<SongRow>, answers: List<String>) {
        _numOfCorrectAnswers.value = 0
        for (i in lyric.indices) {
            if (answers[i] == lyric[i].word) {
                lyric[i].isCorrect = true
                _numOfCorrectAnswers.value?.let { a ->
                    _numOfCorrectAnswers.value = a + 1
                }
            }
            else {
                lyric[i].isCorrect = false
            }
        }
        _statistic.value = lyric
    }
}
