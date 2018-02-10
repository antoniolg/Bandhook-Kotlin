package com.antonioleiva.bandhookkotlin.di

import com.antonioleiva.bandhookkotlin.di.subcomponent.album.AlbumActivityComponent
import com.antonioleiva.bandhookkotlin.di.subcomponent.album.AlbumActivityModule
import com.antonioleiva.bandhookkotlin.di.subcomponent.detail.ArtistActivityComponent
import com.antonioleiva.bandhookkotlin.di.subcomponent.detail.ArtistActivityModule
import com.antonioleiva.bandhookkotlin.di.subcomponent.main.MainActivityComponent
import com.antonioleiva.bandhookkotlin.di.subcomponent.main.MainActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(ApplicationModule::class), (DataModule::class), (RepositoryModule::class),
        (DomainModule::class)]
)
interface ApplicationComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: ArtistActivityModule): ArtistActivityComponent
    fun plus(module: AlbumActivityModule): AlbumActivityComponent
}