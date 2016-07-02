package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.test.ActivityInstrumentationTestCase2
import android.test.UiThreadTest
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.ArtistDetailPagerAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ArtistDetail
import com.antonioleiva.bandhookkotlin.ui.presenter.ArtistPresenter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

/**
 * @author alexey@plainvanillagames.com
 * *         02/07/16.
 */
class ArtistActivityTest : ActivityInstrumentationTestCase2<ArtistActivity>(ArtistActivity::class.java) {

    lateinit var presenter: ArtistPresenter
    lateinit var picasso: Picasso

    lateinit var artistActivity: ArtistActivity

    lateinit var image: ImageView
    lateinit var biographyFragment: BiographyFragment

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
    }

    fun testOnCreate() {
        assertNotNull(artistActivity.image)
        assertEquals(BaseActivity.IMAGE_TRANSITION_NAME, artistActivity.image.transitionName)
        assertNull(artistActivity.title)
        assertNotNull(artistActivity.tabLayout)
        assertNotNull(artistActivity.viewPager)
        assertEquals(ArtistDetailPagerAdapter::class.java, artistActivity.viewPager.adapter.javaClass)
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
}