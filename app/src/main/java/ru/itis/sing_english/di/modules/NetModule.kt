package ru.itis.sing_english.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.sing_english.BuildConfig
import ru.itis.sing_english.data.source.remote.SubtitleRemoteSource
import ru.itis.sing_english.data.source.remote.WordsRemoteSource
import ru.itis.sing_english.data.source.remote.services.SubtitleService
import ru.itis.sing_english.data.source.remote.services.WordsService
import ru.itis.sing_english.data.source.remote.services.YoutubeVideoService
import ru.itis.sing_english.data.source.remote.services.interceptors.SubtitleAuthInterceptor
import ru.itis.sing_english.data.source.remote.services.interceptors.YandexDefReqInterceptor
import ru.itis.sing_english.data.source.remote.services.interceptors.YoutubeAuthInterceptor
import ru.itis.sing_english.data.source.remote.services.interceptors.YoutubeDefReqInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    @Named("ok-youtube")
    fun provideYoutubeOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(YoutubeDefReqInterceptor())
            .addInterceptor(YoutubeAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    @Named("youtube-retrofit")
    fun provideYoutubeRetrofit(@Named("ok-youtube") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.YOUTUBE_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideYoutubeService( @Named("youtube-retrofit")retrofit: Retrofit): YoutubeVideoService {
        return retrofit.create(YoutubeVideoService::class.java)
    }

    @Provides
    @Singleton
    @Named("ok-subtitle")
    fun provideSubtitleOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(SubtitleAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    @Named("subtitle-retrofit")
    fun provideSubtitleRetrofit(@Named("ok-subtitle") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.SUBTITLES_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSubtitleService(@Named("subtitle-retrofit")retrofit: Retrofit): SubtitleService {
        return retrofit.create(SubtitleService::class.java)
    }

    @Provides
    @Singleton
    fun provideSubRemoteSource(subtitleService: SubtitleService) = SubtitleRemoteSource(subtitleService)

    @Provides
    @Singleton
    @Named("ok-yandex")
    fun provideYandexOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(YandexDefReqInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    @Named("yandex-retrofit")
    fun provideYandexRetrofit(@Named("ok-yandex") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.DICT_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideYandexService(@Named("yandex-retrofit")retrofit: Retrofit): WordsService {
        return retrofit.create(WordsService::class.java)
    }

    @Provides
    @Singleton
    fun provideYandexRemoteSource(wordsService: WordsService) = WordsRemoteSource(wordsService)
}
