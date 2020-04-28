package ru.itis.sing_english.data.model

import com.google.gson.annotations.SerializedName

data class DictionaryResponse (
    @SerializedName("def") val def : List<Def>
)

data class Def (
    @SerializedName("text") val word : String,
    @SerializedName("pos") val pos : String,
    @SerializedName("ts") val transcription : String,
    @SerializedName("tr") val translating : List<Tr>
)

data class Tr (
    @SerializedName("text") val transl : String,
    @SerializedName("pos") val pos : String,
    @SerializedName("mean") val mean : List<Mean>,
    @SerializedName("ex") val examples : List<Ex>
)

data class Mean (
    @SerializedName("text") val synonym : String
)

data class Ex (
    @SerializedName("text") val example : String,
    @SerializedName("tr") val tr : List<Tr>
)
