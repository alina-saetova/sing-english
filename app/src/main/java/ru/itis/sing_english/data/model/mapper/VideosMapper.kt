package ru.itis.sing_english.data.model.mapper

import ru.itis.sing_english.data.model.PopVideoItem
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.model.VideoItem

class VideosMapper {

    fun fromResponseToModel(videoItems: List<VideoItem>): List<Video> {
        val list = mutableListOf<Video>()
        for (v in videoItems) {
            val title = splitTitle(v.snippet.title,
                v.snippet.channelTitle)
            list.add(
                Video(0,
                    v.id.videoId,
                    v.snippet.thumbnails.high.url,
                    title.first,
                    title.second,
                    false)
            )
        }
        return list
    }

    fun fromPopResponseToModel(videoItems: List<PopVideoItem>): List<Video> {
        val list = mutableListOf<Video>()
        for (v in videoItems) {
            val title = splitTitle(v.snippet.title,
                v.snippet.channelTitle)
            list.add(
                Video(0,
                    v.id,
                    v.snippet.thumbnails.high.url,
                    title.first,
                    title.second,
                    false)
            )
        }
        return list
    }

    private fun splitTitle(title: String, channelTitle: String): Pair<String, String> {
        val artist = if (channelTitle.contains("VEVO")) {
            channelTitle.removeRange(channelTitle.length - 4, channelTitle.length)
        } else {
            channelTitle
        }
        val sTitle = title.split(" - ")
        return Pair(artist, sTitle[sTitle.size - 1])
    }
}
