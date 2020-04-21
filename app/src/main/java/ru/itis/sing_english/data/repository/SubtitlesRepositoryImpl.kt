package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.model.mapper.SubtitlesMapper
import ru.itis.sing_english.data.services.SubtitleService
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class SubtitlesRepositoryImpl @Inject constructor(
    private var subtitlesApi: SubtitleService,
    private var subtitleDao: SubtitleDao,
    private var mapper: SubtitlesMapper
) : SubtitleRepository {

    override suspend fun getSubtitles(videoId: String): List<Subtitle> {
        val data = retrieveLocalData(videoId)
        return if (data.isNullOrEmpty()) {
            retrieveRemoteData(videoId)
        }
        else {
            data
        }
    }

    private suspend fun retrieveLocalData(videoId: String) = withContext(Dispatchers.IO) {
        val subList = subtitleDao.getSubtitlesByVideoId(videoId)
        subList
    }

    private suspend fun retrieveRemoteData(videoId: String): List<Subtitle> = withContext(Dispatchers.IO) {
        var subtitles = mapper.fromResponseToModel(subtitlesApi.subtitlesByVideoId(videoId), videoId)
        subtitles = cleanSubs(subtitles.toMutableList())
        subtitleDao.insertAll(subtitles)
        subtitles
    }

    private fun cleanSubs(subs: MutableList<Subtitle>): List<Subtitle> {
        for (s in subs) {
            s.row.text = s.row.text.replace("♪ ", "")
            s.row.text = s.row.text.replace(" ♪", "")
            s.row.text = s.row.text.replace("&#39;", "'")
            s.row.text = s.row.text.replace("\n", " ")
        }
        return subs
    }

}
