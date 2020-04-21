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
    @Named("ok-youtube")
    fun provideYoutubeOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(YoutubeAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    @Named("youtube-retrofit")
    fun provideYoutubeRetrofit(@Named("ok-youtube") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.YOUTUBE_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideYoutubeService( @Named("youtube-retrofit")retrofit: Retrofit): YoutubeVideoService {
        return retrofit.create(YoutubeVideoService::class.java)
    }

    @Provides
    @ApplicationScope
    @Named("ok-subtitle")
    fun provideSubtitleOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(SubtitleAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    @Named("subtitle-retrofit")
    fun provideSubtitleRetrofit(@Named("ok-subtitle") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.SUBTITLES_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideSubtitleService(@Named("subtitle-retrofit")retrofit: Retrofit): SubtitleService {
        return retrofit.create(SubtitleService::class.java)
    }

    @Provides
    @ApplicationScope
    @Named("ok-yandex")
    fun provideYandexOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(YandexDefReqInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    @Named("yandex-retrofit")
    fun provideYandexRetrofit(@Named("ok-yandex") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.DICT_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideYandexService(@Named("yandex-retrofit")retrofit: Retrofit): WordService {
        return retrofit.create(WordService::class.java)
    }
}
