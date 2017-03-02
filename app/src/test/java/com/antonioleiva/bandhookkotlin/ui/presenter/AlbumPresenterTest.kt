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

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.interactor.GetAlbumDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumPresenterTest {
    @Mock
    lateinit var albumView: AlbumView
    @Mock
    lateinit var bus: Bus
    @Mock
    lateinit var interactorExecutor: InteractorExecutor
    @Mock
    lateinit var albumRepository: AlbumRepository

    lateinit var albumInteractor: GetAlbumDetailInteractor
    lateinit var albumDetailMapper: AlbumDetailDataMapper

    lateinit var albumPresenter: AlbumPresenter

    private val albumId = "album id"

    @Before
    fun setUp() {
        albumInteractor = GetAlbumDetailInteractor(albumRepository)
        albumDetailMapper = AlbumDetailDataMapper()

        albumPresenter = AlbumPresenter(albumView, bus, albumInteractor, interactorExecutor, albumDetailMapper)
    }

    @Test
    fun testInit() {
        // When
        albumPresenter.init(albumId)

        // Then
        Assert.assertEquals(albumId, albumInteractor.albumId)
        Mockito.verify(interactorExecutor).execute(albumInteractor)
    }

    @Test
    fun testOnEvent() {
        // Given
        val albumDetailEvent = AlbumEvent(Album("album id", "album name", Artist("artist id", "artist name"), "album url", emptyList()))
        val desiredAlbum = albumDetailMapper.transform(albumDetailEvent.album)

        // When
        albumPresenter.onEvent(albumDetailEvent)

        // Then
        Mockito.verify(albumView).showAlbum(desiredAlbum)
    }

    @Test
    fun testOnPause() {
        // When
        albumPresenter.onPause()

        // Then
        Mockito.verify(bus).unregister(albumPresenter)
    }

    @Test
    fun testOnResume() {
        // When
        albumPresenter.onResume()

        // Then
        Mockito.verify(bus).register(albumPresenter)
    }
}