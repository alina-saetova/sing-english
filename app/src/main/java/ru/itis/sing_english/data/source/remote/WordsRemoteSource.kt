package ru.itis.sing_english.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.source.remote.services.WordsService
import javax.inject.Inject

class WordsRemoteSource @Inject constructor(
    private var wordsService: WordsService
) {
    suspend fun retrieveData(text: String): LiveData<DictionaryResponse> = withContext(Dispatchers.IO) {
        val wordsLiveData = MutableLiveData<DictionaryResponse>()
        val word = wordsService.word(text)
        wordsLiveData.postValue(word)
        wordsLiveData
    }



}
