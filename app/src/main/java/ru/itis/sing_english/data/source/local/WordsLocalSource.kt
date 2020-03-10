package ru.itis.sing_english.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.source.local.dao.WordsDao
import javax.inject.Inject

class WordsLocalSource @Inject constructor(
    private val wordsDao: WordsDao
) {
    suspend fun retrieveData(): LiveData<List<Word>> = withContext(Dispatchers.IO) {
        val words = wordsDao.getAllWords()
        val wordsLiveData = MutableLiveData<List<Word>>()
        wordsLiveData.postValue(words)
        wordsLiveData
    }

    suspend fun refreshData(words: List<Word>) = withContext(Dispatchers.IO) {
        wordsDao.insert(words)
    }
}
