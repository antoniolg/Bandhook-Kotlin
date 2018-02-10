package com.antonioleiva.bandhookkotlin.di.subcomponent.album

import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import com.antonioleiva.bandhookkotlin.ui.screens.album.AlbumActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(AlbumActivityModule::class)])
interface AlbumActivityComponent {
    fun injectTo(activity: AlbumActivity)
}