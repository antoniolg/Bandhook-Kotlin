package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmArtist
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmBio
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImage
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImageType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * @author tpom6oh@gmail.com
 * *         29/06/16.
 */
class ArtistMapperTest {
    lateinit var artistMapper: ArtistMapper
    lateinit var artistsList: List<LastFmArtist>
    lateinit var validLastFmArtistWithMegaImage: LastFmArtist
    lateinit var validLastFmArtistWithoutMegaImage: LastFmArtist
    lateinit var invalidLastFmArtist: LastFmArtist
    lateinit var megaLastFmImage: LastFmImage
    lateinit var smallLastFmImage: LastFmImage

    private val coldplayBio = LastFmBio("British rock band formed in 1996")
    private val megaImageUrl = "megaImageOneUrl"
    private val smallImageUrl = "smallImageOneUrl"

    @Before
    fun setUp() {
        megaLastFmImage = LastFmImage(megaImageUrl, LastFmImageType.MEGA.type)
        smallLastFmImage = LastFmImage(smallImageUrl, LastFmImageType.SMALL.type)

        validLastFmArtistWithMegaImage = LastFmArtist("Coldplay", "mbid", "someurl",
                listOf(megaLastFmImage, smallLastFmImage), null, coldplayBio)
        validLastFmArtistWithoutMegaImage = LastFmArtist("Him", "mbid", "someurl",
                listOf(smallLastFmImage, smallLastFmImage), null, null)
        invalidLastFmArtist = LastFmArtist("Unknown", null, "someurl",
                listOf(megaLastFmImage, smallLastFmImage), null, null)

        artistsList = listOf(validLastFmArtistWithMegaImage,
                             invalidLastFmArtist,
                             validLastFmArtistWithoutMegaImage)

        artistMapper = ArtistMapper()
    }

    @Test
    fun testTransformArtists() {
        // When
        val artists = artistMapper.transform(artistsList)

        // Then
        assertEquals(2, artists.size)
        assertEquals(validLastFmArtistWithMegaImage.mbid, artists[0].id)
        assertEquals(validLastFmArtistWithoutMegaImage.mbid, artists[1].id)
    }

    @Test
    fun testTransformArtist_ValidArtistWithMegaImage() {
        // When
        val artist = artistMapper.transform(validLastFmArtistWithMegaImage)

        // Then
        assertEquals(validLastFmArtistWithMegaImage.mbid, artist?.id)
        assertEquals(validLastFmArtistWithMegaImage.name, artist?.name)
        assertEquals(validLastFmArtistWithMegaImage.bio?.content, artist?.bio)
        assertEquals(megaImageUrl, artist?.url)
    }

    @Test
    fun testTransformArtist_ValidArtistWithoutMegaImage() {
        // When
        val artist = artistMapper.transform(validLastFmArtistWithoutMegaImage)

        // Then
        assertEquals(validLastFmArtistWithoutMegaImage.mbid, artist?.id)
        assertEquals(validLastFmArtistWithoutMegaImage.name, artist?.name)
        assertEquals(validLastFmArtistWithoutMegaImage.bio?.content, artist?.bio)
        assertEquals(smallImageUrl, artist?.url)
    }

    @Test
    fun testTransformArtist_InvalidArtist() {
        // When
        val artist = artistMapper.transform(invalidLastFmArtist)

        // Then
        assertNull(artist)
    }
}