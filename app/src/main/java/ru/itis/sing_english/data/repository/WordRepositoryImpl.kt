package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.services.WordService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepositoryImpl @Inject constructor(
    private var wordApi: WordService,
    private var wordDao: WordDao
): WordRepository  {

    override suspend fun getListWords() = withContext(Dispatchers.IO) {
        val words = wordDao.getAllWords()
        words
    }

    override suspend fun getWord(text: String) = withContext(Dispatchers.IO) {
        val word = wordApi.word(text)
        word
    }

    override suspend fun saveWord(response: DictionaryResponse) {
        saveListWords(listOf(response))
    }

    override suspend fun saveListWords(list: List<DictionaryResponse>) {
        val listWords = fromResponseToModel(list)
        withContext(Dispatchers.IO) {
            wordDao.insert(listWords)
        }
    }

    override suspend fun deleteWord(id: Long) {
        withContext(Dispatchers.IO) {
            wordDao.deleteWord(id)
        }
    }

    private fun fromResponseToModel(responses: List<DictionaryResponse>): List<Word> {
        val list = mutableListOf<Word>()
        for (r in responses) {
            list.add(Word(0, r.def[0].word, r.def[0].translating[0].transl))
        }
        return list
    }
}
