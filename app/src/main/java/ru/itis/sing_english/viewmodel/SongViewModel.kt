package ru.itis.sing_english.viewmodel

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.SongRow
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.repository.SubtitleRepository
import javax.inject.Inject
import kotlin.math.abs

class SongViewModel @Inject constructor(
    val repository: SubtitleRepository
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var subsList = mutableListOf<Subtitle>()
    private var rowsList = mutableListOf<SongRow>()
    private var missingWords = mutableListOf<String>()

    //song's rows
    private var _row1 = MutableLiveData<SongRow>()
    val row1: LiveData<SongRow>
        get() = _row1

    private var _row2 = MutableLiveData<SongRow>()
    val row2: LiveData<SongRow>
        get() = _row2

    private var _row3 = MutableLiveData<SongRow>()
    val row3: LiveData<SongRow>
        get() = _row3

    private var _row4 = MutableLiveData<SongRow>()
    val row4: LiveData<SongRow>
        get() = _row4

    private var _row5 = MutableLiveData<SongRow>()
    val row5: LiveData<SongRow>
        get() = _row5

    private var rowsLiveData = mutableListOf(_row1, _row2, _row3, _row4, _row5)

    //buttons' options
    private var _option1 = MutableLiveData<String>()
    val option1: LiveData<String>
        get() = _option1

    private var _option2 = MutableLiveData<String>()
    val option2: LiveData<String>
        get() = _option2

    private var _option3 = MutableLiveData<String>()
    val option3: LiveData<String>
        get() = _option3

    private var _option4 = MutableLiveData<String>()
    val option4: LiveData<String>
        get() = _option4

    private var currentRowIndex = 0

    init {
        _option1.value = DEFAULT_WORDS[0]
        _option2.value = DEFAULT_WORDS[1]
        _option3.value = DEFAULT_WORDS[2]
        _option4.value = DEFAULT_WORDS[3]

        rowsLiveData[2].value = DEFAULT_ROW
    }

    fun loadSong(videoId: String, flag: Boolean) {
        viewModelJob = viewModelScope.launch {
            subsList = repository.getSubtitles(videoId).toMutableList()
            createRowsList(flag)
        }
    }

    fun start() {
        _row1.value = EMPTY_ROW
        _row2.value = EMPTY_ROW
        for(i in 2..4) {
            rowsLiveData[i].value = rowsList.removeAt(0)
        }

        setActiveIndex()
        fillButtons()
        subsList.removeAt(0)
    }

    private fun setActiveIndex() {
        var index = 4
        for ((i, row) in rowsLiveData.withIndex()) {
            if (row.value?.word ?: "" == MISSING_PLACE) {
                index = i
                break
            }
        }
        currentRowIndex = index
    }

    fun onPlaying(second: Float) {
        if (subsList.size == 0) {
            return
        }
        if (abs(second - subsList[0].row.start) < ROW_GAP) {
            if (subsList.size < 3) {
                for (i in 0..(subsList.size + 1)) {
                    rowsLiveData[i].value = rowsLiveData[i + 1].value
                }
                if (subsList.size == 1) {
                    rowsLiveData[3].value = EMPTY_ROW
                }
                setActiveIndex()
                rowsLiveData[4].value = EMPTY_ROW
                subsList.removeAt(0)
                return
            }

            if (rowsLiveData[0].value?.word == MISSING_PLACE) {
                fillButtons()
            }

            for (i in 0..3) {
                rowsLiveData[i].value = rowsLiveData[i + 1].value
            }
            rowsLiveData[4].value = rowsList.removeAt(0)

            setActiveIndex()
            subsList.removeAt(0)
        }
    }

    private var wordsIndex = 0
    private fun fillButtons() {
        if (wordsIndex >= missingWords.size) {
            return
        }
        val indexOfRightAnswer = RANGE_BUTTON.random()
        val listOptions = mutableListOf(_option1, _option2, _option3, _option4)
        listOptions[indexOfRightAnswer].postValue(missingWords[wordsIndex])
        listOptions.removeAt(indexOfRightAnswer)

        val w = missingWords.removeAt(wordsIndex)
        val randomIndices = missingWords.indices.shuffled().toMutableList()
        for (opt in listOptions) {
            opt.postValue(missingWords[randomIndices.removeAt(0)])
        }
        missingWords.add(wordsIndex, w)
        wordsIndex++
    }

    fun answer(view: View) {
        val cur = rowsLiveData[currentRowIndex].value
        val rowCur = SongRow(cur?.first.toString(), (view as Button).text.toString(), cur?.second.toString())
        rowsLiveData[currentRowIndex].value = rowCur
        setActiveIndex()
        fillButtons()
    }

    private fun createRowsList(flag: Boolean) {
        var isMissed = true
        for (sub in subsList) {
            if (!flag) {
                isMissed = !isMissed
            }
            val oldRow = sub.row.text
            val wordsInRow = oldRow.split(" ")
            var randomIndex = 0
            var firstPart = ""
            var secondPart = ""
            if (wordsInRow.size == 2) {
                firstPart = wordsInRow[0]
                randomIndex = 1
            }
            else if (wordsInRow.size > 2) {
                randomIndex = (1 until wordsInRow.size - 1).random()
                firstPart = wordsInRow.subList(0, randomIndex).joinToString(" ")
                secondPart = wordsInRow.subList(randomIndex + 1, wordsInRow.size).joinToString(" ")
            }
            val secretWord = if (isMissed) {
                MISSING_PLACE
            }
            else {
                wordsInRow[randomIndex]
            }
            missingWords.add(wordsInRow[randomIndex])
            val row = SongRow(firstPart, secretWord, secondPart)
            rowsList.add(row)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        val DEFAULT_WORDS = arrayListOf("waiting", "for", "playing", "song")
        val DEFAULT_ROW = SongRow("PRESS",  "PLAY",  "BUTTON")
        val EMPTY_ROW = SongRow("", "", "")
        val RANGE_BUTTON = (0..3)
        const val MISSING_PLACE = "___"
        const val ROW_GAP = 0.06
    }
}
