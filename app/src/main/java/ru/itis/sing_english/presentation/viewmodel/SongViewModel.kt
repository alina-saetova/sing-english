package ru.itis.sing_english.presentation.viewmodel

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
import ru.itis.sing_english.domain.interactors.SongInteractor
import javax.inject.Inject
import kotlin.math.abs

class SongViewModel @Inject constructor(
    private val interactor: SongInteractor
) : ViewModel() {

    private lateinit var viewModelJob: Job
    private var subsList = mutableListOf<Subtitle>()
    private var rowsList = mutableListOf<SongRow>()
    private var missingWords = mutableListOf<String>()
    var fullLyricWithAnswers = mutableListOf<SongRow>()
    var rightAnswers = mutableListOf<String>()

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

    private var rowsLiveData = mutableListOf(_row1, _row2, _row3)

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

    var numM = 0
    fun loadSong(videoId: String, flag: Boolean, num: Int) {
        numM = num
        if (num == 5) {
            rowsLiveData.add(_row4)
            rowsLiveData.add(_row5)
        }
        viewModelJob = viewModelScope.launch {
            subsList = interactor.getSubtitles(videoId)
            createRowsList(flag)
        }
    }

    fun start() {
        _row1.value = EMPTY_ROW
        _row2.value = EMPTY_ROW

        val range = if (numM == 3) {
            1..2
        } else {
            2..4
        }

        for (i in range) {
            rowsLiveData[i].value = rowsList.removeAt(0)
        }

        fillButtons()
        subsList.removeAt(0)
    }

    private fun setActiveIndex() {
        var index = if (numM == 3) {
            2
        } else {
            4
        }
        for ((i, row) in rowsLiveData.withIndex()) {
            if ((row.value?.text ?: "").contains(MISSING_PLACE)) {
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
            rowsLiveData[0].value?.let { fullLyricWithAnswers.add(it) }
            val range: IntRange
            if (numM == 3) {
                range = 0..1
                if (subsList.size == 1) {
                    rowsLiveData[0].value = rowsLiveData[1].value
                    rowsLiveData[1].value = rowsLiveData[2].value
                    rowsLiveData[2].value = EMPTY_ROW
                    fullLyricWithAnswers.removeAt(0)
                    setActiveIndex()
                    subsList.removeAt(0)
                    return
                }
            } else {
                range = 0..3
                if (subsList.size < 3) {
                    for (i in 0..(subsList.size + 1)) {
                        rowsLiveData[i].value = rowsLiveData[i + 1].value
                    }
                    if (subsList.size == 1) {
                        rowsLiveData[3].value = EMPTY_ROW
                    }
                    fullLyricWithAnswers.removeAt(0)
                    setActiveIndex()
                    rowsLiveData[4].value = EMPTY_ROW
                    subsList.removeAt(0)
                    return
                }
            }

            if ((rowsLiveData[0].value?.text ?: "").contains(MISSING_PLACE)
                || isNeedToFillButtons
            ) {
                fillButtons()
                isNeedToFillButtons = false
            }

            for (i in range) {
                rowsLiveData[i].value = rowsLiveData[i + 1].value
            }
            rowsLiveData[numM - 1].value = rowsList.removeAt(0)

            setActiveIndex()
            subsList.removeAt(0)
        }
    }

    private var wordsIndex = 0
    private fun fillButtons() {
        if (wordsIndex >= missingWords.size) {
            return
        }
        setActiveIndex()
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

    private var isNeedToFillButtons = false
    fun answer(view: View) {
        val cur = rowsLiveData[currentRowIndex].value
        val wordsInRow = cur?.text?.split(ROW_SEPARATOR)?.toMutableList()
        wordsInRow?.removeAt(cur.indexOfAnswer)
        wordsInRow?.add(cur.indexOfAnswer, (view as Button).text.toString())
        val newRowText = wordsInRow?.joinToString(ROW_SEPARATOR).toString()
        val rowCur = cur?.wasMissed?.let {
            SongRow(
                newRowText,
                cur.indexOfAnswer,
                it
            )
        }
        rowsLiveData[currentRowIndex].value = rowCur
        if (currentRowIndex != 4 || (_row5.value?.text ?: "").contains(MISSING_PLACE)) {
            fillButtons()
        } else {
            isNeedToFillButtons = true
        }
    }

    private fun createRowsList(flag: Boolean) {
        var isMissed = true
        for (sub in subsList) {
            if (!flag) {
                isMissed = !isMissed
            }
            val oldRow = sub.row.text
            val wordsInRow = oldRow.split(ROW_SEPARATOR)
            val randomIndex = wordsInRow.indices.random()
            var secretWord: String
            if (isMissed) {
                secretWord = MISSING_PLACE
                missingWords.add(wordsInRow[randomIndex])
            } else {
                secretWord = wordsInRow[randomIndex]
            }
            var firstPart = wordsInRow.subList(0, randomIndex).joinToString(ROW_SEPARATOR)
            var secondPart =
                wordsInRow.subList(randomIndex + 1, wordsInRow.size).joinToString(ROW_SEPARATOR)
            if (firstPart.isNotEmpty()) {
                firstPart = firstPart.plus(ROW_SEPARATOR)
            }
            if (secondPart.isNotEmpty()) {
                secondPart = ROW_SEPARATOR.plus(secondPart)
            }
            val textRow = firstPart
                .plus(secretWord)
                .plus(secondPart)
            rightAnswers.add(wordsInRow[randomIndex])
            val row = SongRow(textRow, randomIndex, isMissed)
            rowsList.add(row)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        val DEFAULT_WORDS = arrayListOf("waiting", "for", "playing", "song")
        val DEFAULT_ROW = SongRow("PRESS PLAY BUTTON")
        val EMPTY_ROW = SongRow("")
        val RANGE_BUTTON = (0..3)
        const val ROW_SEPARATOR = " "
        const val MISSING_PLACE = "___"
        const val ROW_GAP = 0.08
    }
}
