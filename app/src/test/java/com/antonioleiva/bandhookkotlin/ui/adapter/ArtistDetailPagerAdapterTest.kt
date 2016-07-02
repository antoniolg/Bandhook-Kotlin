package com.antonioleiva.bandhookkotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

/**
 * @author alexey@plainvanillagames.com
 * *         02/07/16.
 */
@RunWith(MockitoJUnitRunner::class)
class ArtistDetailPagerAdapterTest {

    private val title = "title"

    @Mock
    lateinit var fragmentManager: FragmentManager
    @Mock
    lateinit var fragment: Fragment
    @Mock
    lateinit var anotherFragment: Fragment

    lateinit var artistDetailPagerAdapter: ArtistDetailPagerAdapter

    @org.junit.Before
    fun setUp() {
        artistDetailPagerAdapter = ArtistDetailPagerAdapter(fragmentManager)
    }

    @Test
    fun testAddFragment() {
        // When
        artistDetailPagerAdapter.addFragment(fragment, title)

        // Then
        assertEquals(title, artistDetailPagerAdapter.fragments.get(fragment))
    }

    @Test
    fun testGetItem() {
        // Given
        assertEquals(0, artistDetailPagerAdapter.count)

        // When
        artistDetailPagerAdapter.addFragment(fragment, title)

        // Then
        assertEquals(fragment, artistDetailPagerAdapter.getItem(0))
    }

    @Test
    fun testGetCount() {
        // When
        artistDetailPagerAdapter.addFragment(fragment, title)
        artistDetailPagerAdapter.addFragment(anotherFragment, title)

        // Then
        assertEquals(2, artistDetailPagerAdapter.count)
    }

    @Test
    fun testGetPageTitle() {
        // When
        artistDetailPagerAdapter.addFragment(fragment, title)

        // Then
        assertEquals(title, artistDetailPagerAdapter.getPageTitle(0))
    }
}