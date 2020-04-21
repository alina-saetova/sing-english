package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.local.dao.VideoDao
import ru.itis.sing_english.data.model.PopVideoItem
import ru.itis.sing_english.data.model.PopularVideoResponse
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.model.mapper.VideosMapper
import ru.itis.sing_english.data.services.YoutubeVideoService
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class VideoRepositoryImpl @Inject constructor(
    private var videoApi: YoutubeVideoService,
    private var videoDao: VideoDao,
    private var mapper: VideosMapper
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
        videoDao.delete(video.videoId)
    }

    override suspend fun searchVideos(text: String): List<Video> = withContext(Dispatchers.IO) {
        val response = videoApi.videosByName(text)
        val videos = mapper.fromResponseToModel(response.videoItems)
        checkVideosForLike(videos)
    }

    override suspend fun getPopularVideos(): List<Video> = withContext(Dispatchers.IO) {
        val response = videoApi.popularVideos()
        val videos = mapper.fromPopResponseToModel(filterByCaptions(response))
        checkVideosForLike(videos)
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

    private fun checkVideosForLike(list: List<Video>): List<Video> {
        val favourites = videoDao.getAllVideos()
        for (fav in favourites) {
            loop@ for (video in list) {
                if (fav.videoId == video.videoId) {
                    video.like = true
                    break@loop
                }
            }
        }
        return list
    }
}
