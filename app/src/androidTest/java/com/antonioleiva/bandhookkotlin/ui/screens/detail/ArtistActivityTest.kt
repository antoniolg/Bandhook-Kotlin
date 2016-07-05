package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.test.ActivityInstrumentationTestCase2
import android.test.UiThreadTest
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.ArtistDetailPagerAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ArtistDetail
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.presenter.ArtistPresenter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

/**
 * @author tpom6oh@gmail.com
 * *         02/07/16.
 */
class ArtistActivityTest : ActivityInstrumentationTestCase2<ArtistActivity>(ArtistActivity::class.java) {

    lateinit var presenter: ArtistPresenter
    lateinit var picasso: Picasso

    lateinit var artistActivity: ArtistActivity

    lateinit var image: ImageView
    lateinit var biographyFragment: BiographyFragment
    lateinit var albumsFragment: AlbumsFragment

    public override fun setUp() {
        super.setUp()

        presenter = mock(ArtistPresenter::class.java)
        picasso = mock(Picasso::class.java)

        injectInstrumentation(InstrumentationRegistry.getInstrumentation())

        val intent = Intent(instrumentation.context, ArtistActivity::class.java)
        intent.putExtra("id", "id")
        setActivityIntent(intent)

        artistActivity = getActivity()

        image = artistActivity.image
        biographyFragment = artistActivity.biographyFragment
        albumsFragment = artistActivity.albumsFragment
    }

    fun testOnCreate() {
        // When created
        // Then
        assertNotNull(artistActivity.image)
        assertEquals(BaseActivity.IMAGE_TRANSITION_NAME, artistActivity.image.transitionName)
        assertNull(artistActivity.title)
        assertNotNull(artistActivity.tabLayout)
        assertNotNull(artistActivity.viewPager)
        assertEquals(ArtistDetailPagerAdapter::class.java, artistActivity.viewPager.adapter.javaClass)
        assertEquals(biographyFragment, (artistActivity.viewPager.adapter as ArtistDetailPagerAdapter).getItem(0))
        assertEquals(albumsFragment, (artistActivity.viewPager.adapter as ArtistDetailPagerAdapter).getItem(1))
    }

    fun testViewPagerTitles() {
        // Given
        val desiredBioTitle = instrumentation.targetContext.getString(com.antonioleiva.bandhookkotlin.R.string.bio_fragment_title)
        val desiredAlbumsTitle = instrumentation.targetContext.getString(com.antonioleiva.bandhookkotlin.R.string.albums_fragment_title)

        // Then
        assertEquals(desiredBioTitle, artistActivity.viewPager.adapter.getPageTitle(0))
        assertEquals(desiredAlbumsTitle, artistActivity.viewPager.adapter.getPageTitle(1))
    }

    @UiThreadTest
    fun testLifecycleDelegatedToPresenter() {
        // Given
        artistActivity.presenter = presenter

        // When
        instrumentation.callActivityOnResume(artistActivity)
        instrumentation.callActivityOnPause(artistActivity)

        // Then
        verify(presenter).onPause()
        verify(presenter).onResume()
        verify(presenter).init("id")
    }

    @UiThreadTest
    fun testShowArtist() {
        // Given
        artistActivity.picasso = picasso
        val picassoRequestCreator = mock(RequestCreator::class.java)
        `when`(picasso.load(anyString())).thenReturn(picassoRequestCreator)
        `when`(picassoRequestCreator.fit()).thenReturn(picassoRequestCreator)
        `when`(picassoRequestCreator.centerCrop()).thenReturn(picassoRequestCreator)
        val picassoCallbackArgumentCaptor = ArgumentCaptor.forClass(Callback.EmptyCallback::class.java)
        val artistDetail = ArtistDetail("id", "name", "url", "bio")

        // When
        artistActivity.showArtist(artistDetail)

        // Then
        verify(picasso).load(artistDetail.url)
        verify(picassoRequestCreator).into(eq(image), picassoCallbackArgumentCaptor.capture())

        // When
        picassoCallbackArgumentCaptor.value.onSuccess()

        // Then
        assertEquals(artistDetail.name, artistActivity.supportActionBar?.title)
        assertEquals(artistDetail.bio, biographyFragment.getBiographyText())
    }

    @UiThreadTest
    fun testShowAlbums() {
        // Given
        val albumImageTitles = listOf(ImageTitle("album id", "album name", "album url"))

        // When
        artistActivity.showAlbums(albumImageTitles)

        // Then
        assertEquals(albumImageTitles, albumsFragment.adapter.items)
    }
}