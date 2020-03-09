package ru.itis.sing_english.data.source.remote.services.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.itis.sing_english.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubtitleAuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
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
        return chain.proceed(newRequest)
    }
}
