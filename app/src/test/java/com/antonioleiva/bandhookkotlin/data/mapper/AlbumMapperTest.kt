package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author tpom6oh@gmail.com
 * *         04/07/16.
 */
class AlbumMapperTest {

    lateinit var lastFmAlbum: LastFmAlbum
    lateinit var lastFmAlbumWithoutId: LastFmAlbum
    lateinit var lastFmAlbumWithoutImages: LastFmAlbum

    lateinit var lastFmAlbumDetail: LastFmAlbumDetail
    lateinit var lastFmAlbumDetailWithoutId: LastFmAlbumDetail

    lateinit var lastFmValidAlbums: List<LastFmAlbum>
    lateinit var lastFmInvalidAlbums: List<LastFmAlbum>
    lateinit var lastFmValidAndInvalidAlbums: List<LastFmAlbum>
    lateinit var lastFmEmptyAlbums: List<LastFmAlbum>

    lateinit var albumMapper: AlbumMapper

    private val albumName = "album name"
    private val albumMbid = "album mbid"
    private val albumUrl = "album url"
    private val artistName = "artist name"

    @Before
    fun setUp() {
        val lastFmArtist = LastFmArtist(artistName, "artist mbid", "artist url", emptyList(), null, null)
        val lastFmImages = listOf(LastFmImage("image url", LastFmImageType.MEGA.type))
        val lastFmTracklist = LastFmTracklist(emptyList())

        lastFmAlbum = LastFmAlbum(albumName, albumMbid, albumUrl,
                lastFmArtist, lastFmImages, lastFmTracklist)
        lastFmAlbumWithoutId = LastFmAlbum(albumName, null, albumUrl,
                lastFmArtist, lastFmImages, lastFmTracklist)
        lastFmAlbumWithoutImages = LastFmAlbum(albumName, albumMbid, albumUrl,
                lastFmArtist, emptyList(), lastFmTracklist)

        lastFmValidAlbums = listOf(lastFmAlbum, lastFmAlbum)
        lastFmInvalidAlbums = listOf(lastFmAlbumWithoutId, lastFmAlbumWithoutImages)
        lastFmValidAndInvalidAlbums = listOf(lastFmAlbum, lastFmAlbumWithoutId, lastFmAlbumWithoutImages)
        lastFmEmptyAlbums = emptyList()

        lastFmAlbumDetail = LastFmAlbumDetail(albumName, albumMbid, albumUrl, artistName, "album release date",
                lastFmImages, lastFmTracklist)
        lastFmAlbumDetailWithoutId = LastFmAlbumDetail(albumName, null, albumUrl, artistName, "album release date",
                lastFmImages, lastFmTracklist)

        albumMapper = AlbumMapper()
    }

    @Test
    fun testTransformAlbums_noValidArtists() {
        // When
        val albums = albumMapper.transform(lastFmInvalidAlbums)

        // Then
        assertTrue(albums.isEmpty())
    }

    @Test
    fun testTransformAlbums_withValidArtists() {
        // When
        val albums = albumMapper.transform(lastFmValidAlbums)

        // Then
        assertEquals(2, albums.size)
        assertEquals(lastFmValidAlbums[0].mbid, albums[0].id)
        assertEquals(lastFmValidAlbums[1].mbid, albums[1].id)
    }

    @Test
    fun testTransformAlbums_noArtists() {
        // When
        val albums = albumMapper.transform(lastFmEmptyAlbums)

        // Then
        assertTrue(albums.isEmpty())
    }

    @Test
    fun testTransformAlbums_withMixedArtists() {
        // When
        val albums = albumMapper.transform(lastFmValidAndInvalidAlbums)

        // Then
        assertEquals(1, albums.size)
        assertEquals(lastFmValidAlbums[0].mbid, albums[0].id)
    }

    @Test
    fun testTransformAlbumDetail_validAlbumDetail() {
        // When
        val album = albumMapper.transform(lastFmAlbumDetail)

        // Then
        assertNotNull(album)
        assertEquals(lastFmAlbumDetail.mbid, album?.id)
    }

    @Test
    fun testTransformAlbumDetail_invalidAlbumDetail() {
        // When
        val album = albumMapper.transform(lastFmAlbumDetailWithoutId)

        // Then
        assertNull(album)
    }

    @Test
    fun testTransformAlbum_validAlbum() {
        // When
        val album = albumMapper.transform(lastFmAlbum)

        // Then
        assertNotNull(album)
        assertEquals(lastFmAlbum.mbid, album?.id)
    }

    @Test
    fun testTransformAlbum_invalidAlbum() {
        // When
        val album = albumMapper.transform(lastFmAlbumWithoutId)

        // Then
        assertNull(album)
    }
}