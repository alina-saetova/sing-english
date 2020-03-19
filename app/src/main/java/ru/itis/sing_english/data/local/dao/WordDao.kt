package ru.itis.sing_english.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.itis.sing_english.data.model.Word

@Dao
interface WordDao {

    @Insert
    fun insert(words: List<Word>)

    @Query("SELECT * FROM words")
    fun getAllWords(): List<Word>

    @Query("DELETE FROM words")
    fun deleteAllWords()

    @Delete
    fun deleteWord(word: Word)
}
