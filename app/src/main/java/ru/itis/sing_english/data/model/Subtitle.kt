package ru.itis.sing_english.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.itis.sing_english.data.model.SubtitleResponse

@Entity(tableName = "subtitles")
data class Subtitle (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "video_id")
    val videoId: String,
    @Embedded
    val row: SubtitleResponse
)