package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.model.mapper.WordsMapper
import ru.itis.sing_english.data.services.WordService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepositoryImpl @Inject constructor(
    private var wordApi: WordService,
    private var wordDao: WordDao,
    private var mapper: WordsMapper
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
        val listWords = mapper.fromResponseToModel(list)
        withContext(Dispatchers.IO) {
            wordDao.insert(listWords)
        }
    }

    override suspend fun deleteWord(id: Long) {
        withContext(Dispatchers.IO) {
            wordDao.deleteWord(id)
        }
    }
}
