package ru.itis.sing_english.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.itis.sing_english.data.model.Subtitle

@Dao
interface SubtitlesDao {

    @Insert
    fun insertAll(subtitles: List<Subtitle>)

    @Query("SELECT * FROM subtitles WHERE video_id = :id")
    fun getSubtitlesByVideoId(id: String): List<Subtitle>

    @Query("DELETE FROM subtitles")
    fun deleteAllSubtitles()
}
