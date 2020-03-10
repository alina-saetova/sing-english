package ru.itis.sing_english.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.model.SubtitleResponse
import ru.itis.sing_english.data.source.remote.services.SubtitleService
import javax.inject.Inject

class SubtitleRemoteSource @Inject constructor(
    private var subtitleService: SubtitleService
) {
    suspend fun retrieveData(videoId: String): List<Subtitle> = withContext(Dispatchers.IO) {
        val subList = fromResponseToModel(subtitleService.subtitlesByVideoId(videoId), videoId)
        subList
    }

    private fun fromResponseToModel(list: List<SubtitleResponse>, videoId: String): List<Subtitle> {
        val subList = mutableListOf<Subtitle>()
        for (sub in list) {
            subList.add(Subtitle(0, videoId, sub))
        }
        return subList
    }

}
