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

package com.antonioleiva.bandhookkotlin.data

import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.antonioleiva.bandhookkotlin.data.lastfm.model.*
import com.antonioleiva.bandhookkotlin.data.mapper.ArtistMapper
import com.antonioleiva.bandhookkotlin.data.mock.FakeCall
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import retrofit2.Response
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CloudArtistDataSetTest {

    @Mock
    lateinit var lastFmService: LastFmService

    lateinit var lastFmResponse: LastFmResponse
    lateinit var recommendedArtistList: List<LastFmArtist>
    lateinit var lastFmArtist: LastFmArtist

    val artistMapper = ArtistMapper()

    lateinit var cloudArtistDataSet: CloudArtistDataSet

    private val artistMbid = "artist mbid"
    private val language = "lang"

    private val lastFmAlbumDetail = LastFmAlbumDetail("album name", artistMbid, "album url", "album artist", "album release",
            emptyList(), LastFmTracklist(emptyList()))

    @Before
    fun setUp() {
        lastFmArtist = LastFmArtist("artist name", artistMbid, "artist url", null, null, null)
        recommendedArtistList = listOf(lastFmArtist)

        lastFmResponse = LastFmResponse(LastFmResult(LastFmArtistMatches(emptyList())),
                lastFmArtist, LastFmTopAlbums(emptyList()), LastFmArtistList(recommendedArtistList),
                lastFmAlbumDetail)

        cloudArtistDataSet = CloudArtistDataSet(language, lastFmService)

        `when`(lastFmService.requestSimilar(cloudArtistDataSet.coldplayMbid)).thenReturn(FakeCall(Response.success(lastFmResponse), null))
        `when`(lastFmService.requestArtistInfo(artistMbid, language)).thenReturn(FakeCall(Response.success(lastFmResponse), null))
    }

    @Test
    fun testRequestRecommendedArtists() {
        // When
        val recommendedArtists = cloudArtistDataSet.requestRecommendedArtists()

        // Then
        verify(lastFmService).requestSimilar(cloudArtistDataSet.coldplayMbid)
        assertEquals(artistMapper.transform(recommendedArtistList), recommendedArtists)
    }

    @Test
    fun testRequestArtist() {
        // When
        val requestedArtist = cloudArtistDataSet.requestArtist(artistMbid)

        // Then
        verify(lastFmService).requestArtistInfo(artistMbid, language)
        assertEquals(artistMapper.transform(lastFmArtist), requestedArtist)
    }

    @Test
    fun testRequestArtist_unknownId() {
        // Given
        val unknownArtisMbid = "unknown artist mbid"
        val unknownArtistResponse = LastFmResponse(LastFmResult(LastFmArtistMatches(emptyList())),
                LastFmArtist("unknown artist name", null, "unknown artist url"),
                LastFmTopAlbums(emptyList()),
                LastFmArtistList(emptyList()), lastFmAlbumDetail)
        `when`(lastFmService.requestArtistInfo(unknownArtisMbid, language)).thenReturn(FakeCall(Response.success(unknownArtistResponse), null))

        // When
        val requestedArtist = cloudArtistDataSet.requestArtist(unknownArtisMbid)

        // Then
        verify(lastFmService).requestArtistInfo(unknownArtisMbid, language)
        assertNull(requestedArtist)
    }
}