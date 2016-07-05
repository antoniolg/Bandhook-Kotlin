package com.antonioleiva.bandhookkotlin.domain.interactor

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumDetailEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import org.junit.Assert.assertEquals
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
class GetAlbumDetailInteractorTest {

    @Mock
    lateinit var albumRepository: AlbumRepository

    lateinit var getAlbumDetailInteractor: GetAlbumDetailInteractor

    private val albumId = "album id"

    @Before
    fun setUp() {
        `when`(albumRepository.getAlbum(albumId)).thenReturn(Album("album id", "album name",
                "album url", Artist("artist id", "artist name", null, null, null)))

        getAlbumDetailInteractor = GetAlbumDetailInteractor(albumRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun testInvoke_withoutId() {
        // When
        val event = getAlbumDetailInteractor.invoke()

        // Then expected illegal state exception
    }

    @Test
    fun testInvoke_withId() {
        // Given
        getAlbumDetailInteractor.albumId = albumId

        // When
        val event = getAlbumDetailInteractor.invoke()

        // Then
        assertEquals(AlbumDetailEvent::class.java, event.javaClass)
        assertEquals(albumId, (event as AlbumDetailEvent).album?.id)
    }
}