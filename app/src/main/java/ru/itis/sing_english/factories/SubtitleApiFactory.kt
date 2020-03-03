package ru.itis.sing_english.factories

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.sing_english.BuildConfig
import ru.itis.sing_english.services.SubtitleService

object SubtitleApiFactory {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url().newBuilder()
            .addQueryParameter("key",
                BuildConfig.YOUTUBE_API_KEY
            )
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .header("x-rapidapi-key",
                BuildConfig.SUBTITLES_API_KEY
            )
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }

    private val client by lazy {
        OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.SUBTITLES_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val subtitleService: SubtitleService by lazy { retrofit.create(
        SubtitleService::class.java) }
}
