/*
 * Copyright (C) 2016 Alexey Verein
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

import com.antonioleiva.bandhookkotlin.domain.interactor.GetAlbumDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumEvent
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView

open class AlbumPresenter(
    override val view: AlbumView,
    override val bus: Bus,
    private val albumInteractor: GetAlbumDetailInteractor,
    private val interactorExecutor: InteractorExecutor,
    private val albumDetailMapper: AlbumDetailDataMapper) : Presenter<AlbumView> {

    open fun init(albumId: String) {
        val albumDetailInteractor = albumInteractor
        albumInteractor.albumId = albumId
        interactorExecutor.execute(albumDetailInteractor)
    }

    fun onEvent(event: AlbumEvent) {
        view.showAlbum(albumDetailMapper.transform(event.album))
    }
}