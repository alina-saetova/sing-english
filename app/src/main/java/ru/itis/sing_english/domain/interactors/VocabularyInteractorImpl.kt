package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class VocabularyInteractorImpl @Inject constructor(
    private val repository: WordRepository
) : VocabularyInteractor {

    override suspend fun getWords(): List<Word> {
        return repository.getListWords()
    }

    override suspend fun deleteWord(word: Word) {
        repository.deleteWord(word)
    }
}
