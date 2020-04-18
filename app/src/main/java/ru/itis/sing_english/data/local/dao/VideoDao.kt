package ru.itis.sing_english.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.itis.sing_english.data.model.Video

@Dao
interface VideoDao {

    @Insert
    fun insert(video: Video)

    @Query("DELETE FROM videos WHERE video_id = :videoId")
    fun delete(videoId: String)

    @Query("SELECT * from videos")
    fun getAllVideos() : List<Video>
}
