package ru.itis.sing_english.data.source.repository

import androidx.lifecycle.LiveData
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.source.local.SubtitleLocalSource
import ru.itis.sing_english.data.source.remote.SubtitleRemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubtitlesRepository @Inject constructor(
    private var localSource: SubtitleLocalSource,
    private var remoteSource: SubtitleRemoteSource
) {

    suspend fun getSubtitles(videoId: String):LiveData<List<Subtitle>> {
        val data = localSource.retrieveData(videoId)

        return if (data.value.isNullOrEmpty())
            retrieveRemoteData(videoId)
        else
            retrieveLocalData(videoId)
    }

    private suspend fun retrieveLocalData(videoId: String) = localSource.retrieveData(videoId)

    private suspend fun retrieveRemoteData(videoId: String): LiveData<List<Subtitle>> {
        val subtitles = remoteSource.retrieveData(videoId)
        subtitles.value?.let { localSource.refreshData(it) }
        return subtitles
    }
}
