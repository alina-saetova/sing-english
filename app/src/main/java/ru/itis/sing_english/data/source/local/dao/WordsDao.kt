package ru.itis.sing_english.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.itis.sing_english.data.model.Word

@Dao
interface WordsDao {

    @Insert
    fun insert(word: Word)

    @Query("SELECT * FROM words")
    fun getAllWords(): LiveData<List<Word>>

    @Query("DELETE FROM words")
    fun deleteAllWords()

    @Delete
    fun deleteWord(word: Word)
}
