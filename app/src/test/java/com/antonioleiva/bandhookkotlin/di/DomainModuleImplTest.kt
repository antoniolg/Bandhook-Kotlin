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
        assertNotNull(domainModule.recommendedArtistsInteractorProvider)
    }

    @Test
    fun testGetArtistDetailInteractorProvider() {
        assertNotNull(domainModule.artistDetailInteractorProvider)
    }

    @Test
    fun testGetTopAlbumsInteractorProvider() {
        assertNotNull(domainModule.topAlbumsInteractorProvider)
    }

    @Test
    fun testGetAlbumInteractorProvider() {
        assertNotNull(domainModule.albumInteractorProvider)
    }

    @Test
    fun testGetArtistRepository() {
        assertNotNull(domainModule.artistRepository)
    }

    @Test
    fun testGetAlbumRepository() {
        assertNotNull(domainModule.albumRepository)
    }
}