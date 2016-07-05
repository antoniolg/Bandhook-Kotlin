package com.antonioleiva.bandhookkotlin.di

import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
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
class RepositoryModuleImplTest {

    @Mock
    lateinit var appModule: AppModule
    @Mock
    lateinit var dataModule: DataModule
    @Mock
    lateinit var lastFmService: LastFmService


    lateinit var repositoryModule: RepositoryModuleImpl

    @Before
    fun setUp() {
        `when`(dataModule.lastFmService).thenReturn(lastFmService)
        `when`(appModule.language).thenReturn("language")

        repositoryModule = RepositoryModuleImpl(appModule, dataModule)
    }

    @Test
    fun testGetArtistRepository() {
        // When
        val artistRepository = repositoryModule.artistRepository

        // Then
        assertNotNull(artistRepository)
    }

    @Test
    fun testGetAlbumRepository() {
        // When
        val albumRepository = repositoryModule.albumRepository

        // Then
        assertNotNull(albumRepository)
    }
}