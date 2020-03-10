package ru.itis.sing_english.data.source.repository

import android.util.Log
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

    suspend fun getSubtitles(videoId: String): List<Subtitle> {
        val data = retrieveLocalData(videoId)
        Log.e("LOCAL", data.toString())
        return if (data.isNullOrEmpty())
            retrieveRemoteData(videoId)
        else
            data
    }

    private suspend fun retrieveLocalData(videoId: String) = localSource.retrieveData(videoId)

    private suspend fun retrieveRemoteData(videoId: String): List<Subtitle> {
        val subtitles = remoteSource.retrieveData(videoId)
        localSource.refreshData(subtitles)
        return subtitles
    }

}
