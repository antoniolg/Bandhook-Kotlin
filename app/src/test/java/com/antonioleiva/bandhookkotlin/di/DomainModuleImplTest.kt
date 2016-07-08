package com.antonioleiva.bandhookkotlin.di

import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import org.junit.Assert.assertNotNull
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
class DomainModuleImplTest {

    @Mock
    lateinit var repositoryModule: RepositoryModule
    @Mock
    lateinit var artistRepository: ArtistRepository
    @Mock
    lateinit var albumRepository: AlbumRepository

    lateinit var domainModule: DomainModuleImpl

    @Before
    fun setUp() {
        `when`(repositoryModule.albumRepository).thenReturn(albumRepository)
        `when`(repositoryModule.artistRepository).thenReturn(artistRepository)

        domainModule = DomainModuleImpl(repositoryModule)
    }

    @Test
    fun testGetRecommendedArtistsInteractorProvider() {
        // When
        val recommendedArtistsInteractorProvider = domainModule.recommendedArtistsInteractorProvider

        // Then
        assertNotNull(recommendedArtistsInteractorProvider)
    }

    @Test
    fun testGetArtistDetailInteractorProvider() {
        // When
        val artistDetailInteractor = domainModule.artistDetailInteractorProvider

        // Then
        assertNotNull(artistDetailInteractor)
    }

    @Test
    fun testGetTopAlbumsInteractorProvider() {
        // When
        val topAlbumsInteractorProvider = domainModule.topAlbumsInteractorProvider

        // Then
        assertNotNull(topAlbumsInteractorProvider)
    }

    @Test
    fun testGetAlbumInteractorProvider() {
        // When
        val albumInteractorProvider = domainModule.albumInteractorProvider

        // Then
        assertNotNull(albumInteractorProvider)
    }

    @Test
    fun testGetArtistRepository() {
        // When
        val artistRepository = domainModule.artistRepository

        // Then
        assertNotNull(artistRepository)
    }

    @Test
    fun testGetAlbumRepository() {
        // When
        val albumRepository = domainModule.albumRepository

        // Then
        assertNotNull(albumRepository)
    }
}