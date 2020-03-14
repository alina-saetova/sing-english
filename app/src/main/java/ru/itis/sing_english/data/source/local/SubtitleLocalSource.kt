package ru.itis.sing_english.data.source.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.source.local.dao.SubtitlesDao
import javax.inject.Inject

class SubtitleLocalSource @Inject constructor(
    private var subtitlesDao: SubtitlesDao)
{
    suspend fun retrieveData(videoId: String): List<Subtitle> = withContext(Dispatchers.IO) {
        val subtitles = subtitlesDao.getSubtitlesByVideoId(videoId)
        subtitles
    }

    suspend fun refreshData(subtitles: List<Subtitle>) = withContext(Dispatchers.IO) {
        subtitlesDao.insertAll(subtitles)
    }

}
