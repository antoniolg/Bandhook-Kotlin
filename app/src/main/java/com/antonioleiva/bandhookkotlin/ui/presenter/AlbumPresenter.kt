package com.antonioleiva.bandhookkotlin.ui.presenter

import com.antonioleiva.bandhookkotlin.domain.interactor.GetAlbumDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumDetailEvent
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView

/**
 * @author tpom6oh@gmail.com
 *
 * 05/07/16.
 */
class AlbumPresenter(
        override val view: AlbumView,
        override val bus: Bus,
        val albumInteractor: GetAlbumDetailInteractor,
        val interactorExecutor: InteractorExecutor,
        val albumDetailMapper: AlbumDetailDataMapper) : Presenter<AlbumView> {

    fun init(albumId: String) {
        val albumDetailInteractor = albumInteractor;
        albumInteractor.albumId = albumId
        interactorExecutor.execute(albumDetailInteractor)
    }

    fun onEvent(event: AlbumDetailEvent) {
        view.showAlbum(albumDetailMapper.transform(event.album))
    }
}