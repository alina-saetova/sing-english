package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.repository.WordRepository
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class WordDetailInteractorImpl @Inject constructor(
    val repository: WordRepository
) : WordDetailInteractor {

    override suspend fun getWord(word: String): DictionaryResponse {
        return repository.getWord(word)
    }

    override suspend fun saveWord(word: DictionaryResponse) {
        repository.saveWord(word)
    }
}
