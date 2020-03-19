package ru.itis.sing_english.data.repository

import ru.itis.sing_english.data.model.Subtitle

interface SubtitleRepository {

    suspend fun getSubtitles(videoId: String): List<Subtitle>
}
