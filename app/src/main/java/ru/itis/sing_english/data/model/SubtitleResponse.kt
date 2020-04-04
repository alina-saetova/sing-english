package ru.itis.sing_english.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SubtitleResponse (
    @SerializedName("index") @ColumnInfo(name = "row_num") val index : String,
    @SerializedName("start") @ColumnInfo(name =  "start_of_row")val start : Float,
    @SerializedName("dur") @ColumnInfo(name =  "dur_of_row")val dur : Float,
    @SerializedName("end") @ColumnInfo(name =  "end_of_row")val end : Float,
    @SerializedName("text") @ColumnInfo(name = "text_row") var text : String
)
