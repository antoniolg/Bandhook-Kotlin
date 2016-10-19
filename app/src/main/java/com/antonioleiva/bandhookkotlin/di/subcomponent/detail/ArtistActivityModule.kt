package com.antonioleiva.bandhookkotlin.di.subcomponent.detail

import com.antonioleiva.bandhookkotlin.di.ActivityModule
import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import com.antonioleiva.bandhookkotlin.domain.interactor.GetArtistDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetTopAlbumsInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ArtistDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ImageTitleDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.ArtistPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.detail.AlbumsFragment
import com.antonioleiva.bandhookkotlin.ui.screens.detail.ArtistActivity
import com.antonioleiva.bandhookkotlin.ui.screens.detail.BiographyFragment
import com.antonioleiva.bandhookkotlin.ui.view.ArtistView
import dagger.Module
import dagger.Provides

@Module
class ArtistActivityModule(activity: ArtistActivity) : ActivityModule(activity) {

    @Provides @ActivityScope
    fun provideArtistView(): ArtistView = activity as ArtistView

    @Provides @ActivityScope
    fun provideArtistDataMapper() = ArtistDetailDataMapper()

    @Provides @ActivityScope
    fun provideImageTitleDataMapper() = ImageTitleDataMapper()

    @Provides @ActivityScope
    fun provideActivityPresenter(view: ArtistView,
                                 bus: Bus,
                                 artistDetailInteractor: GetArtistDetailInteractor,
                                 topAlbumsInteractor: GetTopAlbumsInteractor,
                                 interactorExecutor: InteractorExecutor,
                                 detailDataMapper: ArtistDetailDataMapper,
                                 imageTitleDataMapper: ImageTitleDataMapper)
            = ArtistPresenter(view, bus, artistDetailInteractor, topAlbumsInteractor,
            interactorExecutor, detailDataMapper, imageTitleDataMapper)

    @Provides @ActivityScope
    fun provideAlbumsFragment() = AlbumsFragment()

    @Provides @ActivityScope
    fun provideBiographyFragment() = BiographyFragment()
}