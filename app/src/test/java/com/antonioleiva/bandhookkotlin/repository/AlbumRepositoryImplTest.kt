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

package com.antonioleiva.bandhookkotlin.repository

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.entity.Artist
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.repository.datasource.AlbumDataSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumRepositoryImplTest {

    @Mock
    lateinit var firstAlbumDataSource: AlbumDataSource
    @Mock
    lateinit var secondAlbumDataSource: AlbumDataSource

    lateinit var albumInBothDataSources: Album
    lateinit var albumInSecondDataSource: Album
    lateinit var albumRepository: AlbumRepository

    lateinit var albumsInBothDataSources: List<Album>
    lateinit var albumsInSecondDataSource: List<Album>

    private val albumIdInBothDataSources = "album id"
    private val albumIdInSecondDataSource = "second album id"
    private val artistIdInBothDataSources = "artist id"
    private val artistIdInSecondDataSource = "second artist id"
    private val artistName = "artist name"

    @Before
    fun setUp() {

        albumInBothDataSources = Album(albumIdInBothDataSources, "album name", Artist(artistIdInBothDataSources, artistName), "album url", emptyList())
        albumInSecondDataSource = Album(albumIdInSecondDataSource, "album name", Artist(artistIdInBothDataSources, artistName), "album url", emptyList())

        mockRequestAlbumReturns()

        albumsInBothDataSources = listOf(albumInBothDataSources)
        albumsInSecondDataSource = listOf(albumInSecondDataSource)

        mockRequestTopAlbumsReturns()

        albumRepository = AlbumRepositoryImpl(listOf(firstAlbumDataSource, secondAlbumDataSource))
    }

    private fun mockRequestTopAlbumsReturns() {
        `when`(firstAlbumDataSource.requestTopAlbums(null, artistName)).thenReturn(albumsInBothDataSources)
        `when`(firstAlbumDataSource.requestTopAlbums(artistIdInBothDataSources, null)).thenReturn(albumsInBothDataSources)
        `when`(secondAlbumDataSource.requestTopAlbums(artistIdInSecondDataSource, null)).thenReturn(albumsInSecondDataSource)
    }

    private fun mockRequestAlbumReturns() {
        `when`(firstAlbumDataSource.get(albumIdInBothDataSources)).thenReturn(albumInBothDataSources)
        `when`(secondAlbumDataSource.get(albumIdInSecondDataSource)).thenReturn(albumInSecondDataSource)
    }

    @Test
    fun testGetAlbum_existingInBothDataSources() {
        // When
        val album = albumRepository.get(albumIdInBothDataSources)

        // Then
        assertEquals(albumInBothDataSources, album)
    }

    @Test
    fun testGetAlbum_existingOnlyInSecondDataSource() {
        // When
        val album = albumRepository.get(albumIdInSecondDataSource)

        // Then
        assertEquals(albumInSecondDataSource, album)
    }

    @Test
    fun testGetTopAlbums_withArtistId() {
        // When
        val albums = albumRepository.getTopAlbums(artistIdInBothDataSources, null)

        // Then
        assertEquals(albumsInBothDataSources, albums)
    }

    @Test
    fun testGetTopAlbums_withArtistIdExistingOnlyInSecondDataSource() {
        // When
        val albums = albumRepository.getTopAlbums(artistIdInSecondDataSource, null)

        // Then
        assertEquals(albumsInSecondDataSource, albums)
    }

    @Test
    fun testGetTopAlbums_withArtistName() {
        // When
        val albums = albumRepository.getTopAlbums(null, artistName)

        // Then
        assertEquals(albumsInBothDataSources, albums)
    }

    @Test
    fun testGetTopAlbums_withArtistIdAndArtistName() {
        // When
        val albums = albumRepository.getTopAlbums(null, null)

        // Then
        assertTrue(albums.isEmpty())
    }
}
