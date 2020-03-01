package ru.itis.sing_english

import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("search")
    suspend fun videosByName(@Query("q") name: String): SearchResponse
}