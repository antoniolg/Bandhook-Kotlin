package com.antonioleiva.bandhookkotlin.ui.presenter

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.interactor.GetArtistDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetTopAlbumsInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Bus
import com.antonioleiva.bandhookkotlin.domain.interactor.base.InteractorExecutor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.ArtistDetailEvent
import com.antonioleiva.bandhookkotlin.domain.interactor.event.TopAlbumsEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ArtistDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ImageTitleDataMapper
import com.antonioleiva.bandhookkotlin.ui.view.ArtistView
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

/**
 * @author tpom6oh@gmail.com
 * *         04/07/16.
 */
@RunWith(MockitoJUnitRunner::class)
class ArtistPresenterTest {

    @Mock
    lateinit var artistView: ArtistView
    @Mock
    lateinit var bus: Bus
    @Mock
    lateinit var artistRepository: ArtistRepository
    @Mock
    lateinit var albumRepository: AlbumRepository
    @Mock
    lateinit var interactorExecutor: InteractorExecutor

    lateinit var artistDetailInteractor: GetArtistDetailInteractor
    lateinit var topAlbumsInteractor: GetTopAlbumsInteractor
    lateinit var artistDetailMapper: ArtistDetailDataMapper
    lateinit var albumsMapper: ImageTitleDataMapper

    lateinit var artistPresenter: ArtistPresenter

    private val artistId = "artist id"

    @Before
    fun setUp() {
        artistDetailInteractor = GetArtistDetailInteractor(artistRepository)
        topAlbumsInteractor = GetTopAlbumsInteractor(albumRepository)
        artistDetailMapper = ArtistDetailDataMapper()
        albumsMapper = ImageTitleDataMapper()


        artistPresenter = ArtistPresenter(artistView, bus, artistDetailInteractor, topAlbumsInteractor,
                interactorExecutor, artistDetailMapper, albumsMapper)
    }

    @Test
    fun testInitArtistInteractor() {
        // When
        artistPresenter.init(artistId)

        // Then
        assertEquals(artistId, artistDetailInteractor.id)
        verify(interactorExecutor).execute(artistDetailInteractor)
    }

    @Test
    fun testInitAlbumsInteractor() {
        // When
        artistPresenter.init(artistId)

        // Then
        assertEquals(artistId, artistDetailInteractor.id)
        verify(interactorExecutor).execute(artistDetailInteractor)
    }

    @Test
    fun testOnArtistDetailEvent() {
        // Given
        val artistDetailEvent = ArtistDetailEvent(Artist("artist id", "artist name"))
        val desiredArtist = artistDetailMapper.transform(artistDetailEvent.artist)

        // When
        artistPresenter.onEvent(artistDetailEvent)

        // Then
        verify(artistView).showArtist(desiredArtist)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun testOnTopAlbumsEvent() {
        // Given
        val album = Album("album id", "album name", null, Artist("artist id", "artist name"), emptyList())
        val topAlbumsEvent = TopAlbumsEvent(listOf(album))
        val desiredAlbums = albumsMapper.transformAlbums(topAlbumsEvent.topAlbums)

        // When
        artistPresenter.onEvent(topAlbumsEvent)

        // Then
        verify(artistView).showAlbums(desiredAlbums)
    }

    @Test
    fun testOnPause() {
        // When
        artistPresenter.onPause()

        // Then
        verify(bus).unregister(artistPresenter)
    }

    @Test
    fun testOnResume() {
        // When
        artistPresenter.onResume()

        // Then
        verify(bus).register(artistPresenter)
    }

    @Test
    fun testOnAlbumClicked() {
        // Given
        val imageId = "image id"

        // When
        artistPresenter.onAlbumClicked(ImageTitle(imageId, "image name", null))

        // Then
        verify(artistView).navigateToAlbum(imageId)
    }
}