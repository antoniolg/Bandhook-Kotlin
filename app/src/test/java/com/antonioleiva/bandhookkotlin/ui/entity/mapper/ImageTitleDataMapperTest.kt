package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * @author tpom6oh@gmail.com
 * *         04/07/16.
 */
class ImageTitleDataMapperTest {

    lateinit var artists: List<Artist>
    lateinit var albums: List<Album>

    lateinit var imageTitleDataMapper: ImageTitleDataMapper

    @Before
    fun setUp() {
        val artistWithImageUrl = Artist("artist id", "artist name", "artist url")
        val artistWithoutImageUrl = Artist("artist id", "artist name")
        val artistWithEmptyImageUrl = Artist("artist id", "artist name", "")

        artists = listOf(artistWithImageUrl, artistWithoutImageUrl, artistWithEmptyImageUrl)

        val albumWithImageUrl = Album("album id", "album name", "album url", artistWithImageUrl)
        val albumWithoutImageUrl = Album("album id", "album name", null, artistWithImageUrl)
        val albumWithEmptyImageUrl = Album("album id", "album name", "", artistWithImageUrl)

        albums = listOf(albumWithImageUrl, albumWithoutImageUrl, albumWithEmptyImageUrl)

        imageTitleDataMapper = ImageTitleDataMapper()
    }

    @Test
    fun testTransformArtists() {
        // When
        val imageTitles = imageTitleDataMapper.transformArtists(artists)

        // Then
        assertEquals(artists[0].id, imageTitles[0].id)
        assertEquals(artists[0].name, imageTitles[0].name)
        assertEquals(artists[0].url, imageTitles[0].url)

        assertEquals(artists[1].id, imageTitles[1].id)
        assertEquals(artists[1].name, imageTitles[1].name)
        assertEquals(artists[1].url, imageTitles[1].url)

        assertEquals(artists[2].id, imageTitles[2].id)
        assertEquals(artists[2].name, imageTitles[2].name)
        assertNull(imageTitles[2].url)
    }

    @Test
    fun testTransformAlbums() {
        // When
        val imageTitles = imageTitleDataMapper.transformAlbums(albums)

        // Then
        assertEquals(albums[0].id, imageTitles[0].id)
        assertEquals(albums[0].name, imageTitles[0].name)
        assertEquals(albums[0].url, imageTitles[0].url)

        assertEquals(albums[1].id, imageTitles[1].id)
        assertEquals(albums[1].name, imageTitles[1].name)
        assertEquals(albums[1].url, imageTitles[1].url)

        assertEquals(albums[2].id, imageTitles[2].id)
        assertEquals(albums[2].name, imageTitles[2].name)
        assertNull(imageTitles[2].url)
    }
}