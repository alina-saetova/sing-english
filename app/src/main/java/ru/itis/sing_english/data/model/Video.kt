package ru.itis.sing_english.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class Video(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "video_id")
    val videoId: String,
    @ColumnInfo(name = "img_url")
    val imgUrl: String,
    val title: String,
    @ColumnInfo(name = "channel_title")
    val channelTitle: String,
    var like: Boolean
)
