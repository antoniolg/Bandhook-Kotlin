package com.antonioleiva.bandhookkotlin.di

import android.content.*
import com.antonioleiva.bandhookkotlin.*
import com.antonioleiva.bandhookkotlin.BuildConfig
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.data.*
import com.antonioleiva.bandhookkotlin.data.lastfm.*
import com.antonioleiva.bandhookkotlin.domain.*
import com.antonioleiva.bandhookkotlin.domain.interactor.*
import com.antonioleiva.bandhookkotlin.domain.interactor.base.*
import com.antonioleiva.bandhookkotlin.domain.repository.*
import com.antonioleiva.bandhookkotlin.repository.*
import com.birbit.android.jobqueue.*
import com.squareup.picasso.*
import okhttp3.*
import okhttp3.Cache
import okhttp3.logging.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.*
import retrofit2.converter.gson.*
import java.util.*


enum class Qualifiers {
    ApplicationContext,
    LanguageSelection,
    ApiKey,
    CacheDuration
}

fun appModule(app: App) = Kodein.Module {
    bind() from singleton { app }
    bind<Context>(Qualifiers.ApplicationContext) with singleton { app }
    bind<Bus>() with singleton { BusImpl() }
    bind() from singleton { Picasso.Builder(app).build() }
    bind<JobManager>() with singleton { CustomJobManager(app) }
    bind<InteractorExecutor>() with singleton { InteractorExecutorImpl(instance(), instance()) }
    bind<String>(Qualifiers.LanguageSelection) with singleton { Locale.getDefault().language }
}

fun dataModule(appContext: Context) = Kodein.Module {

    bind() from singleton { Cache(appContext.cacheDir, 10 * 1024 * 1024.toLong()) }
    bind(Qualifiers.ApiKey) from singleton { appContext.getString(R.string.last_fm_api_key) }
    bind(Qualifiers.CacheDuration) from singleton { appContext.resources.getInteger(R.integer.cache_duration) }
    bind<Interceptor>() with singleton { LastFmRequestInterceptor(instance(Qualifiers.ApiKey), instance(Qualifiers.CacheDuration)) }

    bind() from singleton {
        OkHttpClient().newBuilder()
                .cache(instance())
                .addInterceptor(instance())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()
    }

    bind() from singleton {
        Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com")
                .client(instance())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    bind() from singleton { instance<Retrofit>().create(LastFmService::class.java) }
}

val domainModule = Kodein.Module {
    bind() from provider { GetRecommendedArtistsInteractor(instance()) }
    bind() from provider { GetArtistDetailInteractor(instance()) }
    bind() from provider { GetTopAlbumsInteractor(instance()) }
    bind() from provider { GetAlbumDetailInteractor(instance()) }
}

val repositoryModule = Kodein.Module {
    bind<ArtistRepository>() with singleton { ArtistRepositoryImpl(listOf(CloudArtistDataSet(instance(Qualifiers.LanguageSelection), instance()))) }
    bind<AlbumRepository>() with singleton { AlbumRepositoryImpl(listOf(CloudAlbumDataSet(instance()))) }
}