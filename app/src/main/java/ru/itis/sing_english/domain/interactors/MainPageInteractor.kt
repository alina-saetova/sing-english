package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Video

interface MainPageInteractor {

    suspend fun getVideos(): List<Video>

    suspend fun searchVideos(text: String): List<Video>

    suspend fun deleteVideo(video: Video)

    suspend fun addVideo(video: Video)
}
