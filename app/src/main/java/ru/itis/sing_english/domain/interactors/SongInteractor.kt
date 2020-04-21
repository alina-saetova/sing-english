package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Subtitle

interface SongInteractor {

    suspend fun getSubtitles(videoId: String): MutableList<Subtitle>
}
