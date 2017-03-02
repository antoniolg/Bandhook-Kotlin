package com.antonioleiva.bandhookkotlin.di

import android.content.Context
import com.antonioleiva.bandhookkotlin.App
import com.antonioleiva.bandhookkotlin.di.qualifier.ApplicationQualifier
import com.antonioleiva.bandhookkotlin.di.qualifier.LanguageSelection
import com.antonioleiva.bandhookkotlin.domain.BusImpl
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.CustomJobManager
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutorImpl
import com.birbit.android.jobqueue.JobManager
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: App) {

    @Provides @Singleton
    fun provideApplication(): App = app

    @Provides @Singleton @ApplicationQualifier
    fun provideApplicationContext(): Context = app

    @Provides @Singleton
    fun provideBus(): Bus = BusImpl()

    @Provides @Singleton
    fun providePicasso(@ApplicationQualifier context: Context): Picasso = Picasso.Builder(context).build()

    @Provides @Singleton
    fun provideJobManager(@ApplicationQualifier context: Context): JobManager = CustomJobManager(context)

    @Provides @Singleton
    fun provideInteractorExecutor(jobManager: JobManager, bus: Bus): InteractorExecutor = InteractorExecutorImpl(jobManager, bus)

    @Provides @Singleton @LanguageSelection
    fun provideLanguageSelection(): String = Locale.getDefault().language
}