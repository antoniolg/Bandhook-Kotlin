package com.antonioleiva.bandhookkotlin.di

import com.antonioleiva.bandhookkotlin.domain.interactor.GetAlbumDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetArtistDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetRecommendedArtistsInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetTopAlbumsInteractor
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRecommendedArtistsInteractor(artistRepository: ArtistRepository)
            = GetRecommendedArtistsInteractor(artistRepository)

    @Provides
    fun provideArtistDetailInteractor(artistRepository: ArtistRepository)
            = GetArtistDetailInteractor(artistRepository)

    @Provides
    fun provideTopAlbumsInteractor(albumRepository: AlbumRepository)
            = GetTopAlbumsInteractor(albumRepository)

    @Provides
    fun provideAlbumsDetailInteractor(albumRepository: AlbumRepository)
            = GetAlbumDetailInteractor(albumRepository)
}