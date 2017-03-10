package com.antonioleiva.bandhookkotlin.di.subcomponent.main

import com.antonioleiva.bandhookkotlin.di.ActivityModule
import com.antonioleiva.bandhookkotlin.di.scope.ActivityScope
import com.antonioleiva.bandhookkotlin.domain.interactor.GetRecommendedArtistsInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.ui.adapter.ImageTitleAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ImageTitleDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.MainPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.main.MainActivity
import com.antonioleiva.bandhookkotlin.ui.view.MainView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(activity: MainActivity) : ActivityModule(activity) {

    @Provides @ActivityScope
    fun provideMainView(): MainView = activity as MainView

    @Provides @ActivityScope
    fun provideImageTitleMapper() = ImageTitleDataMapper()

    @Provides @ActivityScope
    fun provideMainPresenter(view: MainView, bus: Bus,
                             recommendedArtistsInteractor: GetRecommendedArtistsInteractor,
                             interactorExecutor: InteractorExecutor,
                             imageMapper: ImageTitleDataMapper) = MainPresenter(view, bus, recommendedArtistsInteractor,
            interactorExecutor, imageMapper)
}