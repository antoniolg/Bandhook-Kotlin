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

package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmArtist
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmBio
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImage
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImageType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

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