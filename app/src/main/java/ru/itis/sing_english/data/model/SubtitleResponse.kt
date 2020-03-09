package ru.itis.sing_english.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SubtitleResponse (
    @SerializedName("index") @ColumnInfo(name = "row_num") val index : String,
    @SerializedName("start") val start : String,
    @SerializedName("dur") val dur : String,
    @SerializedName("end") val end : String,
    @SerializedName("text") val text : String
)
