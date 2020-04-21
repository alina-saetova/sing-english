package ru.itis.sing_english.domain.interactors

interface ChooseWordsInteractor {

    suspend fun saveWord(word: String)

    suspend fun deleteWord(word: String)
}
