package ru.itis.sing_english.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var translation: String
)
