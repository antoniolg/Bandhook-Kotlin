package com.antonioleiva.bandhookkotlin.di

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule(protected val activity: AppCompatActivity) {

    @Provides @ActivityScope
    fun provideActivity(): AppCompatActivity = activity

    @Provides @ActivityScope
    fun provideActiviyContext(): Context = activity.baseContext
}