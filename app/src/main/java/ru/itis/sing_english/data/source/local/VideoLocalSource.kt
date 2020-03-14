package ru.itis.sing_english.data.source.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.source.local.dao.VideoDao
import javax.inject.Inject

class VideoLocalSource @Inject constructor(
    private var videoDao: VideoDao
) {
    suspend fun retrieveData(): List<Video> = withContext(Dispatchers.IO) {
        val videos = videoDao.getAllVideos()
        videos
    }

    suspend fun refreshData(video: Video) = withContext(Dispatchers.IO) {
        videoDao.insert(video)
    }

    suspend fun checkData(list: List<Video>): List<Video> {
        val favourites = videoDao.getAllVideos()
        for (fav in favourites) {
            loop@for (video in list) {
                if (fav.id == video.id) {
                    video.like = true
                    break@loop
                }
            }
        }
        return list
    }
}
