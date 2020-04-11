package ru.itis.sing_english.data.model.mapper

import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.model.SubtitleResponse

class SubtitlesMapper {

    fun fromResponseToModel(list: List<SubtitleResponse>, videoId: String): List<Subtitle> {
        val subList = mutableListOf<Subtitle>()
        for (sub in list) {
            subList.add(Subtitle(0, videoId, sub))
        }
        return subList
    }
}
