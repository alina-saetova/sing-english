package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.sing_english.BuildConfig
import ru.itis.sing_english.data.services.SubtitleService
import ru.itis.sing_english.data.services.WordService
import ru.itis.sing_english.data.services.YoutubeVideoService
import ru.itis.sing_english.data.services.interceptors.SubtitleAuthInterceptor
import ru.itis.sing_english.data.services.interceptors.YandexDefReqInterceptor
import ru.itis.sing_english.data.services.interceptors.YoutubeAuthInterceptor
import ru.itis.sing_english.di.scope.ApplicationScope
import javax.inject.Named

@Module
class NetModule {

    @Provides
    @ApplicationScope
    @Named(TAG_OK_YOUTUBE)
    fun provideYoutubeOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(YoutubeAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    @Named(TAG_RETROFIT_YOUTUBE)
    fun provideYoutubeRetrofit(@Named(TAG_OK_YOUTUBE) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.YOUTUBE_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideYoutubeService( @Named(TAG_RETROFIT_YOUTUBE)retrofit: Retrofit): YoutubeVideoService {
        return retrofit.create(YoutubeVideoService::class.java)
    }

    @Provides
    @ApplicationScope
    @Named(TAG_OK_SUB)
    fun provideSubtitleOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(SubtitleAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    @Named(TAG_RETROFIT_SUB)
    fun provideSubtitleRetrofit(@Named(TAG_OK_SUB) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.SUBTITLES_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideSubtitleService(@Named(TAG_RETROFIT_SUB)retrofit: Retrofit): SubtitleService {
        return retrofit.create(SubtitleService::class.java)
    }

    @Provides
    @ApplicationScope
    @Named(TAG_OK_YANDEX)
    fun provideYandexOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(YandexDefReqInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    @Named(TAG_RETROFIT_YANDEX)
    fun provideYandexRetrofit(@Named(TAG_OK_YANDEX) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.DICT_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideYandexService(@Named(TAG_RETROFIT_YANDEX)retrofit: Retrofit): WordService {
        return retrofit.create(WordService::class.java)
    }

    companion object {
        const val TAG_OK_YOUTUBE = "ok-youtube"
        const val TAG_RETROFIT_YOUTUBE = "youtube-retrofit"
        const val TAG_OK_SUB = "ok-subtitle"
        const val TAG_RETROFIT_SUB = "subtitle-retrofit"
        const val TAG_OK_YANDEX = "ok-yandex"
        const val TAG_RETROFIT_YANDEX = "yandex-retrofit"
    }
}
