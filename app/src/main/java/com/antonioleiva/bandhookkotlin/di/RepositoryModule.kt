package com.antonioleiva.bandhookkotlin.di

import com.antonioleiva.bandhookkotlin.data.CloudAlbumDataSet
import com.antonioleiva.bandhookkotlin.data.CloudArtistDataSet
import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.antonioleiva.bandhookkotlin.di.qualifier.LanguageSelection
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import com.antonioleiva.bandhookkotlin.repository.AlbumRepositoryImpl
import com.antonioleiva.bandhookkotlin.repository.ArtistRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides @Singleton
    fun provideArtistRepo(@LanguageSelection language: String, lastFmService: LastFmService): ArtistRepository
            = ArtistRepositoryImpl(listOf(CloudArtistDataSet(language, lastFmService)))

    @Provides @Singleton
    fun provideAlbumRepo(lastFmService: LastFmService): AlbumRepository
            = AlbumRepositoryImpl(listOf(CloudAlbumDataSet(lastFmService)))
}