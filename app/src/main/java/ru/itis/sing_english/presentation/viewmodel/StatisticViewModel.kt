package ru.itis.sing_english.presentation.viewmodel

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

    private var _numOfAllAnswers = MutableLiveData<Int>()
    val numOfAllAnswers: LiveData<Int>
        get() = _numOfAllAnswers

    fun loadStatistic(lyric: MutableList<SongRow>, answers: List<String>) {
        _numOfCorrectAnswers.value = 0
        lyric.removeAt(0)
        lyric.removeAt(0)

        var answIndex = 0
        for (i in lyric.indices) {
            val wordsInRow = lyric[i].text.split(ROW_SEPARATOR)
            lyric[i].indicesOfAnswer.remove(RANDOM_CONST)
            lyric[i].indicesOfAnswer.sort()
            for (ind in lyric[i].indicesOfAnswer) {
                if (answers[answIndex] == wordsInRow[ind]) {
                    lyric[i].isCorrect.add(true)
                    _numOfCorrectAnswers.value?.let { a ->
                        _numOfCorrectAnswers.value = a + 1
                    }
                } else {
                    lyric[i].isCorrect.add(false)
                }
                answIndex++
            }
        }
        _statistic.value = lyric
        _numOfAllAnswers.value = answers.size
    }

    companion object {
        const val ROW_SEPARATOR = " "
        const val RANDOM_CONST = 42
    }
}
