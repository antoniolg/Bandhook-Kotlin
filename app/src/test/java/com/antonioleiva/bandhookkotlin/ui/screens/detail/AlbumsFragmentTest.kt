package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.fragment.AlbumsFragmentContainer
import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumsPresenter
import org.junit.Assert.*
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
    @Mock
    lateinit var albumsFragmentContainer: TestAlbumsFragmentContainer
    @Mock
    lateinit var albumsPresenter: AlbumsPresenter

    lateinit var imageTitle: ImageTitle

    lateinit var albumsFragment: AlbumsFragment

    @Before
    fun setUp() {
        imageTitle = ImageTitle("image id", "image name", "image url")

        `when`(layoutInflater.inflate(anyInt(), isNull(ViewGroup::class.java), eq(false))).thenReturn(view)
        `when`(view.findViewById(anyInt())).thenReturn(recyclerView)
        `when`(albumsFragmentContainer.getAlbumsPresenter()).thenReturn(albumsPresenter)

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

    @Test
    fun testOnAttach() {
        // Given
        assertNull(albumsFragment.albumsFragmentContainer)

        // When
        albumsFragment.onAttach(albumsFragmentContainer)

        // Then
        assertEquals(albumsFragment.albumsFragmentContainer, albumsFragmentContainer)
    }


    @Test
    fun testOnDetach() {
        // Given
        albumsFragment.onAttach(albumsFragmentContainer)
        assertNotNull(albumsFragment.albumsFragmentContainer)

        // When
        albumsFragment.onDetach()

        // Then
        assertNull(albumsFragment.albumsFragmentContainer)
    }

    @Test
    fun testAdapterOnClickListener() {
        // Given
        albumsFragment.onAttach(albumsFragmentContainer)
        assertNotNull(albumsFragment.albumsFragmentContainer)
        albumsFragment.onCreateView(layoutInflater, null, null)
        val onClickListener = albumsFragment.adapter.onItemClickListener

        // When
        onClickListener?.invoke(imageTitle)

        // Then
        verify(albumsPresenter).onAlbumClicked(imageTitle)
    }
}

abstract class TestAlbumsFragmentContainer: Context(), AlbumsFragmentContainer