package com.antonioleiva.bandhookkotlin.di.subcomponent.main

import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import com.antonioleiva.bandhookkotlin.ui.screens.main.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {

    fun injectTo(activity: MainActivity)
}