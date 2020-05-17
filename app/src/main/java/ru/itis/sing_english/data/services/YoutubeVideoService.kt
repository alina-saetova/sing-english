package ru.itis.sing_english.data.services

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.sing_english.data.model.PopularVideoResponse
import ru.itis.sing_english.data.model.SearchResponse

interface YoutubeVideoService {

    @GET("search")
    suspend fun videosByName(
        @Query("q") name: String,
        @Query("part") part: String = "snippet",
        @Query("order") order: String = "relevance",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: String = "10",
        @Query("videoCaption") videoCaption: String = "closedCaption",
        @Query("topicId") topicId: String = "/m/04rlf"
    ): SearchResponse

    @GET("search")
    suspend fun videosByTopic(
        @Query("topicId") topicId: String,
        @Query("part") part: String = "snippet",
        @Query("order") order: String = "relevance",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: String = "10",
        @Query("videoCaption") videoCaption: String = "closedCaption"
    ): SearchResponse

    @GET("videos")
    suspend fun popularVideos(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: String = "30",
        @Query("regionCode") regionCode: String = "US",
        @Query("videoCategoryId") videoCategoryId: String = "10"
    ): PopularVideoResponse
}
