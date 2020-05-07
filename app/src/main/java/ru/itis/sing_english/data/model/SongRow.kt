package ru.itis.sing_english.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SongRow(
    var text: String,
    var indexOfAnswer: Int = 0,
    var wasMissed: Boolean = false,
    var isCorrect: Boolean = false
): Parcelable
