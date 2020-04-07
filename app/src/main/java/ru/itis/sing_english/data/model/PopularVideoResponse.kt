package ru.itis.sing_english.data.model

import com.google.gson.annotations.SerializedName

data class PopularVideoResponse(
    @SerializedName("items") val videoItems : List<PopVideoItem>
)

data class PopVideoItem (

    @SerializedName("id") val id : String,
    @SerializedName("snippet") val snippet : PopSnippet,

    @SerializedName("contentDetails")
    val contentDetails: ContentDetails = ContentDetails()
)

data class ContentDetails(
    @SerializedName("caption")
    val caption: String = ""
)

data class PopSnippet (

    @SerializedName("publishedAt") val publishedAt : String,
    @SerializedName("channelId") val channelId : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("thumbnails") val thumbnails : PopThumbnails,
    @SerializedName("channelTitle") val channelTitle : String
)

data class PopThumbnails (
    @SerializedName("high") val high : PopHigh
)

data class PopHigh (

    @SerializedName("url") val url : String
)
