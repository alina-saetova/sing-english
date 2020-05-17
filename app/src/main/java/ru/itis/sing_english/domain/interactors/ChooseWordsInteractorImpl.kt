package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class ChooseWordsInteractorImpl @Inject constructor(
    val repository: WordRepository
) : ChooseWordsInteractor {

    override suspend fun saveWord(word: String) {
        repository.saveWord(word)
    }

    override suspend fun deleteWord(word: String) {
        repository.deleteWord(word.toLowerCase())
    }
}
