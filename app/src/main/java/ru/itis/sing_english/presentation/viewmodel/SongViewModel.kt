package ru.itis.sing_english.presentation.viewmodel

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.itis.sing_english.data.model.DifficultyLevel
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
        //put default values
        _option1.value = DEFAULT_WORDS[0]
        _option2.value = DEFAULT_WORDS[1]
        _option3.value = DEFAULT_WORDS[2]
        _option4.value = DEFAULT_WORDS[3]
        rowsLiveData[2].value = DEFAULT_ROW
    }

    fun loadSong(videoId: String, diffLevel: DifficultyLevel) {
        viewModelJob = viewModelScope.launch {
            subsList = interactor.getSubtitles(videoId)
            createRowsList(diffLevel)
        }
    }

    fun start() {
        //put empty rows on 1, 2 places
        _row1.value = EMPTY_ROW
        _row2.value = EMPTY_ROW

        //put the first 3 rows from the list on 3-5 places
        for (i in 2..4) {
            rowsLiveData[i].value = rowsList.removeAt(0)
        }

        fillButtons()
        subsList.removeAt(0)
    }

    private fun setActiveIndex() {
        var index = 4
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
        //in line with timing, update rows
        if (abs(second - subsList[0].row.start) < ROW_GAP) {
            //before first row goes away, save it for the statistic on next screen
            rowsLiveData[0].value?.let { fullLyricWithAnswers.add(it) }

            //if rows' count < 3 we put empty rows
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

            //check if we need to fill buttons' answers
            if (checkForMissingWords(rowsLiveData[0].value?.text) || isNeedToFillButtons) {
                fillButtons()
                isNeedToFillButtons = false
            }

            //update rows because of timing
            for (i in 0..3) {
                rowsLiveData[i].value = rowsLiveData[i + 1].value
            }
            rowsLiveData[4].value = rowsList.removeAt(0)

            setActiveIndex()
            subsList.removeAt(0)
        }
    }

    private fun checkForMissingWords(text: String?): Boolean {
        val pattern = MISSING_PLACE.toRegex()
        val matches = text?.let { pattern.findAll(it) }
        if (matches?.count() == 2) {
            wordsIndex++
        }
        return matches?.count() != 0
    }

    private var wordsIndex = 0
    private fun fillButtons() {
        if (wordsIndex >= missingWords.size) {
            return
        }
        setActiveIndex()

        //define random button for correct answer
        val indexOfRightAnswer = RANGE_BUTTON.random()
        val listOptions = mutableListOf(_option1, _option2, _option3, _option4)
        listOptions[indexOfRightAnswer].postValue(missingWords[wordsIndex])
        listOptions.removeAt(indexOfRightAnswer)

        //set answers in buttons
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

        //check if user is allowed to answer
        if (cur?.indicesOfAnswer?.size == 0||cur?.indicesOfAnswer?.get(0) ?: 0 == RANDOM_CONST) {
            return
        }

        //set user's answer
        val wordsInRow = cur?.text?.split(ROW_SEPARATOR)?.toMutableList()
        val currentIndexOfMissWord = cur?.indicesOfAnswer?.removeAt(0) ?: 0
        cur?.indicesOfAnswer?.add(currentIndexOfMissWord)
        wordsInRow?.set(currentIndexOfMissWord, (view as Button).text.toString())

        val newRowText = wordsInRow?.joinToString(ROW_SEPARATOR).toString()
        val rowCur = cur?.wasMissed?.let {
            SongRow(
                newRowText,
                cur.indicesOfAnswer,
                it,
                cur.diffLevel
            )
        }
        rowsLiveData[currentRowIndex].value = rowCur
        /*
           check if we need to fill buttons, if not, then all rows is answered
           and next filling happens when there will be a next row
         */
        if (currentRowIndex != 4 || (_row5.value?.text ?: "").contains(MISSING_PLACE)) {
            fillButtons()
        } else {
            isNeedToFillButtons = true
        }
    }

    private fun createRowsList(diffLevel: DifficultyLevel) {
        var isMissed = true
        for (sub in subsList) {
            /*
               If difficulty level = EASY, then missing word will be in every second row,
               so we put a flag to the SongRow, that this row is missing or not
             */
            if (diffLevel == DifficultyLevel.EASY) {
                isMissed = !isMissed
            }

            // according to diff level, create rows for playing
            if (diffLevel == DifficultyLevel.HARD) {
                createRowWithTwoMissWord(sub)
            } else {
                createRowWithOneMissWord(sub, isMissed, diffLevel)
            }
        }
    }

    private fun createRowWithOneMissWord(
        sub: Subtitle,
        isMissed: Boolean,
        diffLevel: DifficultyLevel
    ) {
        //split the row to put there missing word
        val oldRowText = sub.row.text
        val wordsInRow = oldRowText.split(ROW_SEPARATOR).toMutableList()

        //define random index for missing word
        val indices = mutableListOf(wordsInRow.indices.random())

        //put right answers for the statistic on next screen

        //if word is need to be missing, we put on its place some const
        if (isMissed) {
            rightAnswers.add(wordsInRow[indices[0]])
            //missingWords is for buttons' options
            missingWords.add(wordsInRow[indices[0]])
            wordsInRow[indices[0]] = MISSING_PLACE
        } else {
            indices.removeAt(0)
        }
        indices.add(RANDOM_CONST)
        //create new row and put it in the list
        val newTextRow = wordsInRow.joinToString(ROW_SEPARATOR)
        val songRow = SongRow(newTextRow, indices, isMissed, diffLevel)
        Log.e("ALLLLL", songRow.toString())
        rowsList.add(songRow)
    }

    private fun createRowWithTwoMissWord(sub: Subtitle) {
        //split the row to put there missing word
        val oldRow = sub.row.text
        val wordsInRow = oldRow.split(ROW_SEPARATOR).toMutableList()

        //define random indices for missing words
        val indicesOfMissingWords = mutableListOf<Int>()
        val allIndices = wordsInRow.indices.shuffled()
        if (wordsInRow.size > 1) {
            indicesOfMissingWords.add(allIndices[1])
        }
        indicesOfMissingWords.add(allIndices[0])
        indicesOfMissingWords.sort()

        //replace missing words with some const
        for (i in indicesOfMissingWords) {
            rightAnswers.add(wordsInRow[i])
            missingWords.add(wordsInRow[i])
            wordsInRow[i] = MISSING_PLACE
        }

        //create new row and put it in the list
        indicesOfMissingWords.add(RANDOM_CONST)
        val newTextRow = wordsInRow.joinToString(ROW_SEPARATOR)
        val songRow = SongRow(newTextRow, indicesOfMissingWords, diffLevel = DifficultyLevel.HARD)
        Log.e("ALLLLL", songRow.toString())
        rowsList.add(songRow)
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
        const val RANDOM_CONST = 42
    }
}
