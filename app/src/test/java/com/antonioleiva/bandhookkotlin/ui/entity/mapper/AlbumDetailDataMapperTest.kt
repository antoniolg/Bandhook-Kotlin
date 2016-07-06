package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author alexey@plainvanillagames.com
 * *         06/07/16.
 */
class AlbumDetailDataMapperTest {

    lateinit var album: Album

    lateinit var albumDetailDataMapper: AlbumDetailDataMapper

    @Before
    fun setUp() {
        album = Album("album id", "album name", "album url", Artist("artist id", "artist name"), emptyList())

        albumDetailDataMapper = AlbumDetailDataMapper()
    }

    @Test
    fun testTransform() {
        // When
        val transformedAlbum = albumDetailDataMapper.transform(album)

        // Then
        assertNotNull(transformedAlbum)
        assertEquals(album.id, transformedAlbum?.id)
        assertEquals(album.name, transformedAlbum?.name)
        assertEquals(album.url, transformedAlbum?.url)
        assertEquals(album.tracks, transformedAlbum?.tracks)
    }

    @Test
    fun testTransform_null() {
        // When
        val transformedAlbum = albumDetailDataMapper.transform(null)

        // Then
        assertNull(transformedAlbum)
    }
}