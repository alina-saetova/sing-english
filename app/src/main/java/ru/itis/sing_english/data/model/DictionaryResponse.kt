package ru.itis.sing_english.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "saved_words")
data class DictionaryResponse (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("def") val def : List<Def>
)

data class Def (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("text") val word : String,
    @SerializedName("pos") val pos : String,
    @SerializedName("ts") val transcription : String,
    @SerializedName("tr") val translating : List<Tr>
)

data class Tr (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("text") val transl : String,
    @SerializedName("pos") val pos : String,
    @SerializedName("mean") val mean : List<Mean>,
    @SerializedName("ex") val examples : List<Ex>
)

data class Mean (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("text") val synonym : String
)

data class Ex (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("ex") val example : String,
    @SerializedName("tr") val tr : List<Tr>
)
