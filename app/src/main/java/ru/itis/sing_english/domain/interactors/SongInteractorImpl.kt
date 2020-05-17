package ru.itis.sing_english.domain.interactors

import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.repository.SubtitleRepository
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class SongInteractorImpl @Inject constructor(
    val repository: SubtitleRepository
) : SongInteractor {

    override suspend fun getSubtitles(videoId: String): MutableList<Subtitle> {
        return repository.getSubtitles(videoId).toMutableList()
    }
}
