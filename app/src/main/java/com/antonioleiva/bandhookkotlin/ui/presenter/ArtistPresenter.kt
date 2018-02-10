/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.antonioleiva.bandhookkotlin.ui.presenter

import com.antonioleiva.bandhookkotlin.domain.interactor.GetArtistDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetTopAlbumsInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.ArtistDetailEvent
import com.antonioleiva.bandhookkotlin.domain.interactor.event.TopAlbumsEvent
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ArtistDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ImageTitleDataMapper
import com.antonioleiva.bandhookkotlin.ui.view.ArtistView

open class ArtistPresenter(
    override val view: ArtistView,
    override val bus: Bus,
    private val artistDetailInteractor: GetArtistDetailInteractor,
    private val topAlbumsInteractor: GetTopAlbumsInteractor,
    private val interactorExecutor: InteractorExecutor,
    private val artistDetailMapper: ArtistDetailDataMapper,
    private val albumsMapper: ImageTitleDataMapper) : Presenter<ArtistView>, AlbumsPresenter {

    open fun init(artistId: String) {
        val artistDetailInteractor = artistDetailInteractor
        artistDetailInteractor.id = artistId
        interactorExecutor.execute(artistDetailInteractor)

        val topAlbumsInteractor = topAlbumsInteractor
        topAlbumsInteractor.artistId = artistId
        interactorExecutor.execute(this.topAlbumsInteractor)
    }

    fun onEvent(event: ArtistDetailEvent) {
        view.showArtist(artistDetailMapper.transform(event.artist))
    }

    fun onEvent(event: TopAlbumsEvent) {
        view.showAlbums(albumsMapper.transformAlbums(event.topAlbums))
    }

    override fun onAlbumClicked(item: ImageTitle) {
        view.navigateToAlbum(item.id)
    }
}