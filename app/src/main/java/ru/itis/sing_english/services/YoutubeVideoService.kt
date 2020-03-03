package ru.itis.sing_english.services

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.sing_english.responses.SearchResponse

interface YoutubeVideoService {

    @GET("search")
    suspend fun videosByName(@Query("q") name: String): SearchResponse
}