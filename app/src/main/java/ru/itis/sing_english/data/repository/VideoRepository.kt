package ru.itis.sing_english.data.repository

import ru.itis.sing_english.data.model.Video

interface VideoRepository {

    suspend fun getFavouriteVideos(videoId: String): List<Video>

    suspend fun addVideoToFavourite(video: Video)

    suspend fun searchVideos(text: String): List<Video>
}