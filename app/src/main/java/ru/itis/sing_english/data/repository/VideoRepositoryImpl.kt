package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.SearchResponse
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.local.dao.VideoDao
import ru.itis.sing_english.data.services.YoutubeVideoService
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private var videoApi: YoutubeVideoService,
    private var videoDao: VideoDao
) : VideoRepository {

    override suspend fun getFavouriteVideos(): List<Video> = withContext(Dispatchers.IO) {
        val videos = videoDao.getAllVideos()
        videos
    }

    override suspend fun addVideoToFavourite(video: Video) = withContext(Dispatchers.IO) {
        video.like = true
        videoDao.insert(video)
    }

    override suspend fun searchVideos(text: String): List<Video> {
        val response = withContext(Dispatchers.IO) {
            videoApi.videosByName(text)
        }
        val videos = fromResponseToModel(response)
        return checkData(videos)
    }

    private fun checkData(list: List<Video>): List<Video> {
        val favourites = videoDao.getAllVideos()
        for (fav in favourites) {
            loop@for (video in list) {
                if (fav.videoId == video.videoId) {
                    video.like = true
                    break@loop
                }
            }
        }
        return list
    }

    private fun fromResponseToModel(response: SearchResponse): List<Video> {
        val list = mutableListOf<Video>()
        for (v in response.videoItems) {
            val title = splitTitle(v.snippet.title,
                v.snippet.channelTitle)
            list.add(Video(0,
                v.id.videoId,
                v.snippet.thumbnails.high.url,
                title.first,
                title.second,
                false)
            )
        }
        return list
    }

    private fun splitTitle(title: String, channelTitle: String): Pair<String, String> {
        val artist = if (channelTitle.contains("VEVO")) {
            channelTitle.removeRange(channelTitle.length - 4, channelTitle.length)
        } else {
            channelTitle
        }
        val sTitle = title.split(" - ")
        return Pair(artist, sTitle[sTitle.size - 1])
    }

}
