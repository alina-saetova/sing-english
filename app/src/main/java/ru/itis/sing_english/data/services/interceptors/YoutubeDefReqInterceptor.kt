package ru.itis.sing_english.data.services.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YoutubeDefReqInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url()
            .newBuilder()
            .addQueryParameter("part", "snippet")
            .addQueryParameter("order", "relevance")
            .addQueryParameter("type", "video")
            .addQueryParameter("maxResults", "3")
            .addQueryParameter("videoCaption", "closedCaption")
            .addQueryParameter("topicId", "/m/04rlf")
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
