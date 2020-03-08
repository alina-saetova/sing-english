package ru.itis.sing_english.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.sing_english.data.model.SubtitleResponse

interface SubtitleService {

    @GET("{id}")
    suspend fun subtitlesByVideoId(@Path("id") videoId: String) : List<SubtitleResponse>
}
