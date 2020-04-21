package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Word

interface QuizInteractor {

    suspend fun getWords(): List<Word>
}
