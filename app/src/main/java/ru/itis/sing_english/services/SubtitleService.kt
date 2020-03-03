package ru.itis.sing_english.services

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.sing_english.responses.SubtitleResponse

interface SubtitleService {

    @GET("{id}")
    suspend fun subtitlesByVideoId(@Path("id") videoId: String) : List<SubtitleResponse>
}
