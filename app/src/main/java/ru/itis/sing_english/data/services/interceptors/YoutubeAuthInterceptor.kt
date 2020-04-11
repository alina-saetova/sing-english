package ru.itis.sing_english.data.services.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.itis.sing_english.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YoutubeAuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("key",
                BuildConfig.YOUTUBE_API_KEY
            )
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
