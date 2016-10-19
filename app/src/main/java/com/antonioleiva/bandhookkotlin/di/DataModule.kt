package com.antonioleiva.bandhookkotlin.di

import android.content.Context
import com.antonioleiva.bandhookkotlin.BuildConfig
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmRequestInterceptor
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.antonioleiva.bandhookkotlin.di.qualifier.ApiKey
import com.antonioleiva.bandhookkotlin.di.qualifier.ApplicationQualifier
import com.antonioleiva.bandhookkotlin.di.qualifier.CacheDuration
import com.squareup.okhttp.Cache
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import retrofit.RestAdapter
import retrofit.client.OkClient
import javax.inject.Singleton

@Module
class DataModule {

    @Provides @Singleton
    fun provideCache(@ApplicationQualifier context: Context) = Cache(context.cacheDir, 10 * 1024 * 1024.toLong())

    @Provides @Singleton @ApiKey
    fun provideApiKey(@ApplicationQualifier context: Context): String = context.getString(R.string.last_fm_api_key)

    @Provides @Singleton @CacheDuration
    fun provideCacheDuration(@ApplicationQualifier context: Context) = context.resources.getInteger(R.integer.cache_duration)

    @Provides @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val okHttp = OkHttpClient()
        okHttp.cache = cache
        return okHttp
    }

    @Provides @Singleton
    fun provideOkClient(okHttpClient: OkHttpClient) = OkClient(okHttpClient)

    @Provides @Singleton
    fun provideRequestInterceptor(@ApiKey apiKey: String, @CacheDuration cacheDuration: Int)
            = LastFmRequestInterceptor(apiKey, cacheDuration)

    @Provides @Singleton
    fun provideRestAdapter(okClient: OkClient, lastFmRequestInterceptor: LastFmRequestInterceptor): RestAdapter {
        return RestAdapter.Builder()
                .setEndpoint("http://ws.audioscrobbler.com")
                .setRequestInterceptor(lastFmRequestInterceptor)
                .setLogLevel(if (BuildConfig.DEBUG) RestAdapter.LogLevel.FULL else RestAdapter.LogLevel.NONE)
                .setClient(okClient)
                .build()
    }

    @Provides @Singleton
    fun providesLastFmService(restAdapter: RestAdapter): LastFmService = restAdapter.create(LastFmService::class.java)
}