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
import com.antonioleiva.bandhookkotlin.repository.dataset.AlbumDataSet
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
    lateinit var firstAlbumDataSet: AlbumDataSet
    @Mock
    lateinit var secondAlbumDataSet: AlbumDataSet

    lateinit var albumInBothDataSets: Album
    lateinit var albumInSecondDataSet: Album
    lateinit var albumRepository: AlbumRepository

    lateinit var albumsInBothDataSets: List<Album>
    lateinit var albumsInSecondDataSet: List<Album>

    private val albumIdInBothDataSets = "album id"
    private val albumIdInSecondDataSet = "second album id"
    private val artistIdInBothDataSets = "artist id"
    private val artistIdInSecondDataSet = "second artist id"
    private val artistName = "artist name"

    @Before
    fun setUp() {

        albumInBothDataSets = Album(albumIdInBothDataSets, "album name", Artist(artistIdInBothDataSets, artistName), "album url", emptyList())
        albumInSecondDataSet = Album(albumIdInSecondDataSet, "album name", Artist(artistIdInBothDataSets, artistName), "album url", emptyList())

        mockRequestAlbumReturns()

        albumsInBothDataSets = listOf(albumInBothDataSets)
        albumsInSecondDataSet = listOf(albumInSecondDataSet)

        mockRequestTopAlbumsReturns()

        albumRepository = AlbumRepositoryImpl(listOf(firstAlbumDataSet, secondAlbumDataSet))
    }

    private fun mockRequestTopAlbumsReturns() {
        `when`(firstAlbumDataSet.requestTopAlbums(null, artistName)).thenReturn(albumsInBothDataSets)
        `when`(firstAlbumDataSet.requestTopAlbums(artistIdInBothDataSets, null)).thenReturn(albumsInBothDataSets)
        `when`(secondAlbumDataSet.requestTopAlbums(artistIdInSecondDataSet, null)).thenReturn(albumsInSecondDataSet)
    }

    private fun mockRequestAlbumReturns() {
        `when`(firstAlbumDataSet.requestAlbum(albumIdInBothDataSets)).thenReturn(albumInBothDataSets)
        `when`(secondAlbumDataSet.requestAlbum(albumIdInSecondDataSet)).thenReturn(albumInSecondDataSet)
    }

    @Test
    fun testGetAlbum_existingInBothDataSets() {
        // When
        val album = albumRepository.getAlbum(albumIdInBothDataSets)

        // Then
        assertEquals(albumInBothDataSets, album)
    }

    @Test
    fun testGetAlbum_existingOnlyInSecondDataSet() {
        // When
        val album = albumRepository.getAlbum(albumIdInSecondDataSet)

        // Then
        assertEquals(albumInSecondDataSet, album)
    }

    @Test
    fun testGetTopAlbums_withArtistId() {
        // When
        val albums = albumRepository.getTopAlbums(artistIdInBothDataSets, null)

        // Then
        assertEquals(albumsInBothDataSets, albums)
    }

    @Test
    fun testGetTopAlbums_withArtistIdExistingOnlyInSecondDataSet() {
        // When
        val albums = albumRepository.getTopAlbums(artistIdInSecondDataSet, null)

        // Then
        assertEquals(albumsInSecondDataSet, albums)
    }

    @Test
    fun testGetTopAlbums_withArtistName() {
        // When
        val albums = albumRepository.getTopAlbums(null, artistName)

        // Then
        assertEquals(albumsInBothDataSets, albums)
    }

    @Test
    fun testGetTopAlbums_withArtistIdAndArtistName() {
        // When
        val albums = albumRepository.getTopAlbums(null, null)

        // Then
        assertTrue(albums.isEmpty())
    }
}