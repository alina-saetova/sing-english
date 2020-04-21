package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Word

interface VocabularyInteractor {

    suspend fun getWords(): List<Word>

    suspend fun deleteWord(word: Word)
}
