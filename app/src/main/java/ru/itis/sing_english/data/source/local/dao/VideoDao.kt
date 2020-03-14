package ru.itis.sing_english.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.itis.sing_english.data.model.Video

@Dao
interface VideoDao {

    @Insert
    fun insert(video: Video)

    @Delete
    fun delete(video: Video)

    @Query("SELECT * from videos")
    fun getAllVideos() : List<Video>
}
