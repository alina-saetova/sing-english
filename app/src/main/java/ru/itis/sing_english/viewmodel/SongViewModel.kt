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

    private var _previousSub = MutableLiveData<SongRow>()
    val previousSub: LiveData<SongRow>
        get() = _previousSub

    private var _activeSub = MutableLiveData<SongRow>()
    val activeSub: LiveData<SongRow>
        get() = _activeSub

    private var _nextSub = MutableLiveData<SongRow>()
    val nextSub: LiveData<SongRow>
        get() = _nextSub

    private var _firstOption = MutableLiveData<String>()
    val firstOption: LiveData<String>
        get() = _firstOption

    private var _secondOption = MutableLiveData<String>()
    val secondOption: LiveData<String>
        get() = _secondOption

    private var _thirdOption = MutableLiveData<String>()
    val thirdOption: LiveData<String>
        get() = _thirdOption

    private var _fourthOption = MutableLiveData<String>()
    val fourthOption: LiveData<String>
        get() = _fourthOption

    private var _userAnswer = MutableLiveData<String>()
    val userAnswer: LiveData<String>
        get() = _userAnswer

//    init {
//        loadSong()
//    }

    fun start() {
        _previousSub.value = SongRow("", "", "")
        _userAnswer.value = MISSING_PLACE
//        fillButtons()
//        rowsList.removeAt(0)
//        subsList.removeAt(0)
    }

    fun onPlaying(second: Float) {
//        Log.e("onPlay", second.toString())
        if (abs(second - subsList[0].row.start) < ROW_GAP) {
            if (subsList.size == 1) return
            _activeSub.value = rowsList[0]
            _userAnswer.value = MISSING_PLACE
            fillButtons()
            rowsList.removeAt(0)
            subsList.removeAt(0)
        }
    }

    private var currentIndex = 0
    private fun fillButtons() {
        val indexOfRightAnswer = (0..3).random()
        val listOptions = mutableListOf(_firstOption, _secondOption, _thirdOption, _fourthOption)
        listOptions[indexOfRightAnswer].postValue(missingWords[currentIndex])
        listOptions.removeAt(indexOfRightAnswer)

        val w = missingWords.removeAt(currentIndex)
        val randomIndices = missingWords.indices.shuffled().toMutableList()
        for (opt in listOptions) {
            opt.postValue(missingWords[randomIndices.removeAt(0)])
        }
        missingWords.add(currentIndex, w)
        currentIndex++
    }

    fun loadSong(videoId: String) {
        viewModelJob = viewModelScope.launch {
            subsList = repository.getSubtitles(videoId).toMutableList()
//            Log.e("SUBS", subsList.toString())
            createRowsList()
        }

        _firstOption.value = DEFAULT_WORDS[0]
        _secondOption.value = DEFAULT_WORDS[1]
        _thirdOption.value = DEFAULT_WORDS[2]
        _fourthOption.value = DEFAULT_WORDS[3]
    }

    private fun createRowsList() {
        for (sub in subsList) {
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
            val secretWord = wordsInRow[randomIndex]
            val row = SongRow(firstPart, secretWord, secondPart)
//                Log.e("PARTING", row.toString())
            missingWords.add(secretWord)
            rowsList.add(row)
        }
    }

    fun answer(view: View) {
        _userAnswer.value =  (view as Button).text.toString()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        val DEFAULT_WORDS = arrayListOf("waiting", "for", "playing", "song")
        const val MISSING_PLACE = "_____"
        const val ROW_GAP = 0.06
    }
}
