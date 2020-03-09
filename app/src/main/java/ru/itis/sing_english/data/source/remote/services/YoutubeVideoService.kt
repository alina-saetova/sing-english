package ru.itis.sing_english.data.source.remote.services

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.sing_english.data.model.SearchResponse

interface YoutubeVideoService {

    @GET("search")
    suspend fun videosByName(@Query("q") name: String): SearchResponse
}
