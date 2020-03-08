package ru.itis.sing_english.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.itis.sing_english.data.model.Subtitle

@Dao
interface SubtitlesDao {

    @Insert
    fun insert(subtitle: Subtitle)

    @Query("SELECT * FROM subtitles WHERE video_id = :id ORDER BY row_num ASC")
    fun getSubtitlesByVideoId(id: String): LiveData<List<Subtitle>>

    @Query("DELETE FROM subtitles")
    fun deleteAllSubtitles()
}