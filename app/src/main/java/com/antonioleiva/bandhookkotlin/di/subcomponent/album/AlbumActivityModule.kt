package com.antonioleiva.bandhookkotlin.di.subcomponent.album

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.antonioleiva.bandhookkotlin.di.ActivityModule
import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import com.antonioleiva.bandhookkotlin.domain.interactor.GetAlbumDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.ui.adapter.TracksAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.TrackDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.album.AlbumActivity
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView
import dagger.Module
import dagger.Provides

@Module
class AlbumActivityModule(activity: AlbumActivity) : ActivityModule(activity) {

    @Provides @ActivityScope
    fun provideAlbumView(): AlbumView = activity as AlbumView

    @Provides @ActivityScope
    fun provideAlbumDataMapper() = AlbumDetailDataMapper()

    @Provides @ActivityScope
    fun provideTrackDataMapper() = TrackDataMapper()

    @Provides @ActivityScope
    fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

    @Provides @ActivityScope
    fun provideTracksAdapter() = TracksAdapter()

    @Provides @ActivityScope
    fun provideAlbumPresenter(view: AlbumView,
                              bus: Bus,
                              albumInteractor: GetAlbumDetailInteractor,
                              interactorExecutor: InteractorExecutor,
                              albumDetailDataMapper: AlbumDetailDataMapper)
            = AlbumPresenter(view, bus, albumInteractor,
            interactorExecutor, albumDetailDataMapper)
}