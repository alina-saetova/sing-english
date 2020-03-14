package ru.itis.sing_english.data.source.repository

import androidx.lifecycle.LiveData
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.source.local.WordsLocalSource
import ru.itis.sing_english.data.source.remote.WordsRemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsRepository @Inject constructor(
    private var localSource: WordsLocalSource,
    private var remoteSource: WordsRemoteSource
)  {

    suspend fun getListWords() = localSource.retrieveData()

    suspend fun getWord(text: String) = remoteSource.retrieveData(text)

    suspend fun saveWord(response: DictionaryResponse) {
        saveListWords(listOf(response))
    }

    suspend fun saveListWords(list: List<DictionaryResponse>) {
        val listWords = fromResponseToModel(list)
        localSource.refreshData(listWords)
    }

    private fun fromResponseToModel(responses: List<DictionaryResponse>): List<Word> {
        val list = mutableListOf<Word>()
        for (r in responses) {
            list.add(Word(0, r.def[0].word, r.def[0].translating[0].transl))
        }
        return list
    }
}
