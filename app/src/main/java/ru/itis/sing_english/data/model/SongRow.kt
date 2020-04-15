package ru.itis.sing_english.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SongRow(
    var first: String,
    var word: String,
    var second: String,
    var wasMissed: Boolean = false,
    var isCorrect: Boolean = false
): Parcelable
