package com.antonioleiva.bandhookkotlin.di.subcomponent.detail

import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import com.antonioleiva.bandhookkotlin.ui.screens.detail.ArtistActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(ArtistActivityModule::class)])
interface ArtistActivityComponent {

    fun injectTo(activity: ArtistActivity)
}