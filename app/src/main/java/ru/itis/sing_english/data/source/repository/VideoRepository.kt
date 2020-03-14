package ru.itis.sing_english.data.source.repository

import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.source.local.VideoLocalSource
import ru.itis.sing_english.data.source.remote.VideoRemoteSource
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private var localSource: VideoLocalSource,
    private var remoteSource: VideoRemoteSource
) {

    suspend fun getFavouriteVideos(videoId: String): List<Video> = localSource.retrieveData()

    suspend fun addVideoToFavourite(video: Video) {
        localSource.refreshData(video)
    }

    suspend fun searchVideos(text: String): List<Video> {
        val videos = remoteSource.retrieveData(text)
        return localSource.checkData(videos)
    }

}
