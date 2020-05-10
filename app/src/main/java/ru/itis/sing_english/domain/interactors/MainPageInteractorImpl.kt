package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.repository.VideoRepository
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class MainPageInteractorImpl @Inject constructor(
    val repository: VideoRepository
) : MainPageInteractor {

    override suspend fun getPopularVideos(): List<Video> {
        return repository.getPopularVideos()
    }

    override suspend fun getVideosByTopic(topic: String): List<Video> {
        return repository.getVideosByTopic(topic)
    }

    override suspend fun searchVideos(text: String): List<Video> {
        return repository.searchVideos(text)
    }

    override suspend fun deleteVideo(video: Video) {
        repository.deleteVideoFromFavourites(video)
    }

    override suspend fun addVideo(video: Video) {
        repository.addVideoToFavourite(video)
    }
}
