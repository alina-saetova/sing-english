package ru.itis.sing_english.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.sing_english.data.source.local.dao.SubtitlesDao
import ru.itis.sing_english.data.source.local.dao.WordsDao
import ru.itis.sing_english.data.model.Subtitle
import ru.itis.sing_english.data.model.Word

@Database(entities = [Subtitle::class, Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subtitleDao(): SubtitlesDao
    abstract fun wordDao(): WordsDao
}
