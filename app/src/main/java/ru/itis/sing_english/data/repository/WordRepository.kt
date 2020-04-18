package ru.itis.sing_english.data.repository

import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word

interface WordRepository {

    suspend fun getListWords(): List<Word>
    suspend fun getWord(text: String): DictionaryResponse
    suspend fun saveWord(response: DictionaryResponse)
    suspend fun saveWord(text: String)
    suspend fun saveResponseList(list: List<DictionaryResponse>)
    suspend fun deleteWord(word: Word)
    suspend fun deleteWord(word:String)
}
