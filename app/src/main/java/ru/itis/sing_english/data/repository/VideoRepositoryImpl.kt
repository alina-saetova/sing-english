package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.local.dao.VideoDao
import ru.itis.sing_english.data.model.*
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

    override suspend fun deleteVideoFromFavourites(video: Video) = withContext(Dispatchers.IO) {
        videoDao.delete(video)
    }

    override suspend fun searchVideos(text: String): List<Video> = withContext(Dispatchers.IO) {
        val response = videoApi.videosByName(text)
        val videos = fromResponseToModel(response.videoItems)
        checkData(videos)
    }

    override suspend fun getPopularVideos(): List<Video> = withContext(Dispatchers.IO) {
        val response = videoApi.popularVideos()
        val videos = fromPopResponseToModel(filterByCaptions(response))
        checkData(videos)
    }

    private fun filterByCaptions(response: PopularVideoResponse): List<PopVideoItem> {
        val videoListCaptions = mutableListOf<PopVideoItem>()
        for (video in response.videoItems) {
            if (video.contentDetails.caption == "true") {
                videoListCaptions.add(video)
            }
        }
        return videoListCaptions
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

    private fun fromResponseToModel(videoItems: List<VideoItem>): List<Video> {
        val list = mutableListOf<Video>()
        for (v in videoItems) {
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

    private fun fromPopResponseToModel(videoItems: List<PopVideoItem>): List<Video> {
        val list = mutableListOf<Video>()
        for (v in videoItems) {
            val title = splitTitle(v.snippet.title,
                v.snippet.channelTitle)
            list.add(Video(0,
                v.id,
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
