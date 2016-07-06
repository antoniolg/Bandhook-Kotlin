package com.antonioleiva.bandhookkotlin.ui.presenter

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.interactor.GetAlbumDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumDetailEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

/**
 * @author alexey@plainvanillagames.com
 * *         06/07/16.
 */
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
        val albumDetailEvent = AlbumDetailEvent(Album("album id", "album name", "album url", Artist("artist id", "artist name"), emptyList()))
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