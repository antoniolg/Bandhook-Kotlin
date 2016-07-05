package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

/**
 * @author tpom6oh@gmail.com
 * *         04/07/16.
 */
@RunWith(MockitoJUnitRunner::class)
class AlbumsFragmentTest {

    @Mock
    lateinit var layoutInflater: LayoutInflater
    @Mock
    lateinit var view: View
    @Mock
    lateinit var recyclerView: RecyclerView

    lateinit var albumsFragment: AlbumsFragment

    @Before
    fun setUp() {
        `when`(layoutInflater.inflate(anyInt(), isNull(ViewGroup::class.java), eq(false))).thenReturn(view)
        `when`(view.findViewById(anyInt())).thenReturn(recyclerView)

        albumsFragment = AlbumsFragment()
    }

    @Test
    fun testOnCreateView() {
        // When
        val createdView = albumsFragment.onCreateView(layoutInflater, null, null)

        // Then
        assertNotNull(albumsFragment.adapter)
        assertEquals(view, createdView)
    }
}