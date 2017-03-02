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
import com.antonioleiva.bandhookkotlin.data.mapper.AlbumMapper
import com.antonioleiva.bandhookkotlin.data.mock.FakeCall
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import retrofit2.Response
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CloudAlbumDataSetTest {

    @Mock
    lateinit var lastFmService: LastFmService

    lateinit var cloudAlbumDataSet: CloudAlbumDataSet
    lateinit var lastFmResponse: LastFmResponse
    lateinit var knownAlbumDetail: LastFmAlbumDetail
    lateinit var unknownAlbumDetail: LastFmAlbumDetail
    lateinit var album: LastFmAlbum
    lateinit var artist: LastFmArtist
    lateinit var topAlbums: LastFmTopAlbums
    lateinit var albums:  List<LastFmAlbum>

    private val albumMapper = AlbumMapper()
    private val albumMbid = "album mbid"
    private val artistMbid: String = "artist mbid"
    private val artistName: String = "artist name"

    @Before
    fun setUp() {
        cloudAlbumDataSet = CloudAlbumDataSet(lastFmService)

        artist = LastFmArtist(artistName, artistMbid, "artist url", emptyList(), null, null)
        album = LastFmAlbum("album name", albumMbid, "album url",
                artist, emptyList(), LastFmTracklist(emptyList()))
        knownAlbumDetail = LastFmAlbumDetail("album name", albumMbid, "album url", "album artist",
                "album release date", emptyList(), LastFmTracklist(emptyList()))
        unknownAlbumDetail = LastFmAlbumDetail("", null, "", "", "", emptyList(), LastFmTracklist(emptyList()))

        albums = listOf(album)
        topAlbums = LastFmTopAlbums(listOf(album))

        lastFmResponse = LastFmResponse(LastFmResult(LastFmArtistMatches(emptyList())), artist, topAlbums, LastFmArtistList(emptyList()),
                knownAlbumDetail)

        `when`(lastFmService.requestAlbum(albumMbid)).thenReturn(FakeCall(Response.success(lastFmResponse), null))
        `when`(lastFmService.requestAlbums(anyString(), anyString())).thenReturn(FakeCall(Response.success(lastFmResponse), null))
    }

    @Test
    fun testRequestAlbum_knownAlbum() {
        // When
        val album = cloudAlbumDataSet.requestAlbum(albumMbid)

        // Then
        verify(lastFmService).requestAlbum(albumMbid)
        assertEquals(albumMapper.transform(lastFmResponse.album), album)
    }

    @Test
    fun testRequestAlbum_unknownAlbum() {
        // Given
        val unknownAlbumResponse = LastFmResponse(LastFmResult(LastFmArtistMatches(emptyList())), artist, topAlbums,
                LastFmArtistList(emptyList()), unknownAlbumDetail)
        `when`(lastFmService.requestAlbum(albumMbid)).thenReturn(FakeCall(Response.success(unknownAlbumResponse), null))

        // When
        val album = cloudAlbumDataSet.requestAlbum(albumMbid)

        // Then
        verify(lastFmService).requestAlbum(albumMbid)
        assertNull(album)
    }

    @Test
    fun testRequestTopAlbums_byArtistMbid() {
        // When
        val albums = cloudAlbumDataSet.requestTopAlbums(artistMbid, null)

        // Then
        verify(lastFmService).requestAlbums(artistMbid, "")
        assertEquals(albumMapper.transform(lastFmResponse.topAlbums.albums), albums)
    }

    @Test
    fun testRequestTopAlbums_byArtistName() {
        // When
        val albums = cloudAlbumDataSet.requestTopAlbums(null, artistName)

        // Then
        verify(lastFmService).requestAlbums("", artistName)
        assertEquals(albumMapper.transform(lastFmResponse.topAlbums.albums), albums)
    }

    @Test
    fun testRequestTopAlbums_byArtistNameAndMbid() {
        // When
        val albums = cloudAlbumDataSet.requestTopAlbums(artistMbid, artistName)

        // Then
        verify(lastFmService).requestAlbums(artistMbid, artistName)
        assertEquals(albumMapper.transform(lastFmResponse.topAlbums.albums), albums)
    }

    @Test
    fun testRequestTopAlbums_noArguments() {
        // When
        val albums = cloudAlbumDataSet.requestTopAlbums(null, null)

        // Then
        verify(lastFmService, never()).requestAlbums(anyString(), anyString())
        assertTrue(albums.isEmpty())
    }
}