package ru.itis.sing_english.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.model.SubtitleResponse
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.services.SubtitleService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubtitlesRepositoryImpl @Inject constructor(
    private var subtitlesApi: SubtitleService,
    private var subtitleDao: SubtitleDao
) : SubtitleRepository {

    override suspend fun getSubtitles(videoId: String): List<Subtitle> {
        val data = retrieveLocalData(videoId)
        return if (data.isNullOrEmpty())
            retrieveRemoteData(videoId)
        else
            data
    }

    private suspend fun retrieveLocalData(videoId: String) = withContext(Dispatchers.IO) {
        val subList = fromResponseToModel(subtitlesApi.subtitlesByVideoId(videoId), videoId)
//        subList = cleanSubs(subList)
        subList
    }

    private suspend fun retrieveRemoteData(videoId: String): List<Subtitle> = withContext(Dispatchers.IO) {
        val subtitles = subtitleDao.getSubtitlesByVideoId(videoId)
        subtitleDao.insertAll(subtitles)
        subtitles
    }

    private fun fromResponseToModel(list: List<SubtitleResponse>, videoId: String): List<Subtitle> {
        val subList = mutableListOf<Subtitle>()
        for (sub in list) {
            subList.add(Subtitle(0, videoId, sub))
        }
        return subList
    }

    //    private fun cleanSubs(subs: List<Subtitle>): List<Subtitle> {
//        for (s in subs) {
//            s.row.text.replace("&#39;".toRegex(), "\'")
//        }
//        return subs
//    }

}
