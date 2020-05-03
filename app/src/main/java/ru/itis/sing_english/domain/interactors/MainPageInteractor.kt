package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Video

interface MainPageInteractor {

    suspend fun getPopularVideos(): List<Video>

    suspend fun getVideosByTopic(topic: String): List<Video>

    suspend fun searchVideos(text: String): List<Video>

    suspend fun deleteVideo(video: Video)

    suspend fun addVideo(video: Video)
}
