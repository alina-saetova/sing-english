package ru.itis.sing_english

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @SerializedName("kind") val kind : String,
    @SerializedName("etag") val etag : String,
    @SerializedName("nextPageToken") val nextPageToken : String,
    @SerializedName("regionCode") val regionCode : String,
    @SerializedName("pageInfo") val pageInfo : PageInfo,
    @SerializedName("items") val videoItems : List<VideoItem>
)

data class PageInfo (

    @SerializedName("totalResults") val totalResults : Int,
    @SerializedName("resultsPerPage") val resultsPerPage : Int
)

data class VideoItem (

    @SerializedName("kind") val kind : String,
    @SerializedName("etag") val etag : String,
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
    @SerializedName("channelTitle") val channelTitle : String,
    @SerializedName("liveBroadcastContent") val liveBroadcastContent : String
)

data class Thumbnails (

    @SerializedName("default") val default : Default,
    @SerializedName("medium") val medium : Medium,
    @SerializedName("high") val high : High
)

data class Default (

    @SerializedName("url") val url : String,
    @SerializedName("width") val width : String,
    @SerializedName("height") val height : String
)

data class Medium (

    @SerializedName("url") val url : String,
    @SerializedName("width") val width : String,
    @SerializedName("height") val height : String
)

data class High (

    @SerializedName("url") val url : String,
    @SerializedName("width") val width : String,
    @SerializedName("height") val height : String
)