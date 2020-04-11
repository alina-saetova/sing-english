package ru.itis.sing_english.data.model.mapper

import ru.itis.sing_english.data.model.DictionaryResponse
import ru.itis.sing_english.data.model.Word

class WordsMapper {

    fun fromResponseToModel(responses: List<DictionaryResponse>): List<Word> {
        val list = mutableListOf<Word>()
        for (r in responses) {
            list.add(Word(0, r.def[0].word, r.def[0].translating[0].transl))
        }
        return list
    }
}
