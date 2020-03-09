package ru.itis.sing_english.data.source.remote

import android.os.Build
import androidx.annotation.RequiresApi
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
    suspend fun retrieveData(videoId: String): LiveData<List<Subtitle>> = withContext(Dispatchers.IO) {
        val subsLiveData = MutableLiveData<List<Subtitle>>()
        val subList = fromResponseToModel(subtitleService.subtitlesByVideoId(videoId), videoId)

        subsLiveData.postValue(subList)
        subsLiveData
    }

    private fun fromResponseToModel(list: List<SubtitleResponse>, videoId: String): List<Subtitle> {
        val subList: MutableList<Subtitle> = emptyList<Subtitle>().toMutableList()
        for (sub in list) {
            subList.add(Subtitle(0, videoId, sub))
        }
        return subList
    }

}