package ru.itis.sing_english.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val videoItems : List<VideoItem>
)

data class VideoItem (

    @SerializedName("id") val id : Id,
    @SerializedName("snippet") val snippet : Snippet
)

data class Id (

    @SerializedName("kind") val kind : String,
    @SerializedName("videoId") val videoId : String
)

data class Snippet (

    @SerializedName("publishedAt") val publishedAt : String,
    @SerializedName("channelId") val channelId : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("thumbnails") val thumbnails : Thumbnails,
    @SerializedName("channelTitle") val channelTitle : String
)

data class Thumbnails (
    @SerializedName("high") val high : High
)

data class High (

    @SerializedName("url") val url : String
)
