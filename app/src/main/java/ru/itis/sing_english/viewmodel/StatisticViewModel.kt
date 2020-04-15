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

    fun loadStatistic(lyric: List<SongRow>, answers: List<String>) {
        for (i in lyric.indices) {
            lyric[i].isCorrect = answers[i] == lyric[i].word
        }
        _statistic.value = lyric
    }
}
