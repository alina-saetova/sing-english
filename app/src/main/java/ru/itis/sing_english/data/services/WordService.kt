package ru.itis.sing_english.data.services

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.sing_english.data.model.DictionaryResponse

interface WordService {

    @GET("lookup")
    suspend fun word(@Query("text") text: String): DictionaryResponse
}
