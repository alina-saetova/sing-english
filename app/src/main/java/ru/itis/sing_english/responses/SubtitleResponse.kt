package ru.itis.sing_english.responses

import com.google.gson.annotations.SerializedName

data class SubtitleResponse (
    @SerializedName("index") val index : String,
    @SerializedName("start") val start : String,
    @SerializedName("dur") val dur : String,
    @SerializedName("end") val end : String,
    @SerializedName("text") val text : String
)