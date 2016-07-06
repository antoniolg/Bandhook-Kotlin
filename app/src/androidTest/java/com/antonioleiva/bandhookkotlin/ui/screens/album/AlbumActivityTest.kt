package com.antonioleiva.bandhookkotlin.ui.screens.album

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.test.ActivityInstrumentationTestCase2
import android.test.UiThreadTest
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.entity.AlbumDetail
import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.detail.ArtistActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

/**
 * @author alexey@plainvanillagames.com
 * *         06/07/16.
 */
class AlbumActivityTest : ActivityInstrumentationTestCase2<AlbumActivity>(AlbumActivity::class.java) {

    lateinit var presenter: AlbumPresenter
    lateinit var picasso: Picasso

    lateinit var albumActivity: AlbumActivity

    lateinit var image: ImageView

    public override fun setUp() {
        super.setUp()

        presenter = mock(AlbumPresenter::class.java)
        picasso = mock(Picasso::class.java)

        injectInstrumentation(InstrumentationRegistry.getInstrumentation())

        val intent = Intent(instrumentation.context, ArtistActivity::class.java)
        intent.putExtra("id", "id")
        setActivityIntent(intent)

        albumActivity = getActivity()

        image = albumActivity.image
    }

    fun testOnCreate() {
        // When created
        // Then
        assertNotNull(albumActivity.image)
        assertEquals(BaseActivity.IMAGE_TRANSITION_NAME, albumActivity.image.transitionName)
    }

    @UiThreadTest
    fun testLifecycleDelegatedToPresenter() {
        // Given
        albumActivity.presenter = presenter

        // When
        instrumentation.callActivityOnResume(albumActivity)
        instrumentation.callActivityOnPause(albumActivity)

        // Then
        verify(presenter).onPause()
        verify(presenter).onResume()
        verify(presenter).init("id")
    }

    @UiThreadTest
    fun testShowAlbum() {
        // Given
        albumActivity.picasso = picasso
        val picassoRequestCreator = mock(RequestCreator::class.java)
        `when`(picasso.load(anyString())).thenReturn(picassoRequestCreator)
        `when`(picassoRequestCreator.fit()).thenReturn(picassoRequestCreator)
        `when`(picassoRequestCreator.centerCrop()).thenReturn(picassoRequestCreator)
        val picassoCallbackArgumentCaptor = ArgumentCaptor.forClass(Callback.EmptyCallback::class.java)
        val albumDetail = AlbumDetail("id", "name", "url", emptyList())
        val desiredTracksDescription = albumDetail.tracks.map { it.name }.joinToString()

        // When
        albumActivity.showAlbum(albumDetail)

        // Then
        verify(picasso).load(albumDetail.url)
        verify(picassoRequestCreator).into(eq(image), picassoCallbackArgumentCaptor.capture())

        // When
        picassoCallbackArgumentCaptor.value.onSuccess()

        // Then
        assertEquals(desiredTracksDescription, albumActivity.text.text)
    }

    @UiThreadTest
    fun testShowAlbum_nothingToShow() {
        // Given
        albumActivity.picasso = picasso

        // When
        albumActivity.showAlbum(null)

        // Then
        verify(picasso, never()).load(anyString())
        assertTrue(albumActivity.isFinishing)
    }
}