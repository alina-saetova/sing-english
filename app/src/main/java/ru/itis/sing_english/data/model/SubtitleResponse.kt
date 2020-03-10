package ru.itis.sing_english.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SubtitleResponse (
    @SerializedName("index") @ColumnInfo(name = "row_num") val index : String,
    @SerializedName("start") @ColumnInfo(name =  "start_of_row")val start : String,
    @SerializedName("dur") @ColumnInfo(name =  "dur_of_row")val dur : String,
    @SerializedName("end") @ColumnInfo(name =  "end_of_row")val end : String,
    @SerializedName("text") @ColumnInfo(name = "text_row") val text : String
)
