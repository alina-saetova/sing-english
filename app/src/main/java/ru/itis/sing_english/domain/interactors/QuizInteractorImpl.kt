package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class QuizInteractorImpl @Inject constructor(
    val repository: WordRepository
) : QuizInteractor {

    override suspend fun getWords(): List<Word> {
        return repository.getListWords()
    }
}
