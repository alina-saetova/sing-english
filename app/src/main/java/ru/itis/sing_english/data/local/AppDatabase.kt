package ru.itis.sing_english.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.sing_english.data.local.dao.SubtitleDao
import ru.itis.sing_english.data.local.dao.WordDao
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.model.Video
import ru.itis.sing_english.data.model.Word
import ru.itis.sing_english.data.local.dao.VideoDao

@Database(entities = [Subtitle::class, Word::class, Video::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subtitleDao(): SubtitleDao
    abstract fun wordDao(): WordDao
    abstract fun videoDao(): VideoDao
}
