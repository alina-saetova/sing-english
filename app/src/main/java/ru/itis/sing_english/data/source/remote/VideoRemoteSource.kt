package ru.itis.sing_english.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.SearchResponse
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.source.remote.services.YoutubeVideoService
import javax.inject.Inject

class VideoRemoteSource @Inject constructor(
    private var youtubeVideoService: YoutubeVideoService
) {
    suspend fun retrieveData(text: String): List<Video> = withContext(Dispatchers.IO) {
        val response = youtubeVideoService.videosByName(text)
        val models = fromResponseToModel(response)
        models
    }

    private fun fromResponseToModel(response: SearchResponse): List<Video> {
        val list = mutableListOf<Video>()
        for (v in response.videoItems) {
            list.add(Video(0,
                v.id.videoId,
                v.snippet.thumbnails.high.url,
                v.snippet.title,
                v.snippet.channelTitle,
                false)
            )
        }
        return list
    }
}
