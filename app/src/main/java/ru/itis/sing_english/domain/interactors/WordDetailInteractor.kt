package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.DictionaryResponse

interface WordDetailInteractor {

    suspend fun getWord(word: String): DictionaryResponse

    suspend fun saveWord(word: DictionaryResponse)
}
