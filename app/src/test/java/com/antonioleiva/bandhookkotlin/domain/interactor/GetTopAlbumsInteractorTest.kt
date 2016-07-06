package com.antonioleiva.bandhookkotlin.domain.interactor

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.interactor.event.TopAlbumsEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

/**
 * @author tpom6oh@gmail.com
 * *         04/07/16.
 */
@RunWith(MockitoJUnitRunner::class)
class GetTopAlbumsInteractorTest {

    @Mock
    lateinit var albumRepository: AlbumRepository

    lateinit var album: Album

    lateinit var getTopAlbumsInteractor: GetTopAlbumsInteractor

    private val artistId = "artist id"
    private val artistName = "artist name"

    @Before
    fun setUp() {
        album = Album("album id", "Album name", null, Artist("artist id", "artist name"), emptyList())

        `when`(albumRepository.getTopAlbums(artistId, artistName)).thenReturn(listOf(album))
        `when`(albumRepository.getTopAlbums(null, artistName)).thenReturn(listOf(album))
        `when`(albumRepository.getTopAlbums(artistId, null)).thenReturn(listOf(album))

        getTopAlbumsInteractor = GetTopAlbumsInteractor(albumRepository)
    }

    @Test
    fun testInvoke_withArtistId() {
        // Given
        getTopAlbumsInteractor.artistId = artistId

        // When
        val event = getTopAlbumsInteractor.invoke()

        // Then
        Assert.assertEquals(TopAlbumsEvent::class.java, event.javaClass)
        Assert.assertEquals(album, (event as TopAlbumsEvent).topAlbums[0])
    }

    @Test
    fun testInvoke_withArtistName() {
        // Given
        getTopAlbumsInteractor.artistName = artistName

        // When
        val event = getTopAlbumsInteractor.invoke()

        // Then
        Assert.assertEquals(TopAlbumsEvent::class.java, event.javaClass)
        Assert.assertEquals(album, (event as TopAlbumsEvent).topAlbums[0])
    }

    @Test(expected = IllegalStateException::class)
    fun testInvoke_withoutData() {
        // When
        getTopAlbumsInteractor.invoke()

        // Then expected illegal state exception
    }
}