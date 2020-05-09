package ru.itis.sing_english.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SongRow(
    var text: String,
    var indicesOfAnswer: MutableList<Int> = mutableListOf(),
    var wasMissed: Boolean = true,
    var diffLevel: DifficultyLevel = DifficultyLevel.EASY,
    var isCorrect: MutableList<Boolean> = mutableListOf()
): Parcelable
