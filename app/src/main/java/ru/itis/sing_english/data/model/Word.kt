package ru.itis.sing_english.data.model

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var translation: String
)