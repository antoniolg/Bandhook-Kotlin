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

package com.antonioleiva.bandhookkotlin.ui.screens.album

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.ViewPropertyAnimator
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.di.ApplicationComponent
import com.antonioleiva.bandhookkotlin.di.subcomponent.album.AlbumActivityModule
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.TracksAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.AlbumDetail
import com.antonioleiva.bandhookkotlin.ui.entity.TrackDetail
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.TrackDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumPresenter
import com.antonioleiva.bandhookkotlin.ui.util.getNavigationId
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.dimen
import javax.inject.Inject

class AlbumActivity : BaseActivity<AlbumLayout>(), AlbumView {

    override val ui = AlbumLayout()

    companion object {
        private const val LIST_ANIMATION_START_DELAY = 500L
        private const val NO_TRANSLATION = 0f
        private const val TRANSPARENT = 0f
    }

    val albumListBreakingEdgeHeight by lazy { dimen(R.dimen.album_breaking_edge_height).toFloat() }

    @Inject
    @VisibleForTesting
    lateinit var presenter: AlbumPresenter

    @Inject
    lateinit var trackDataMapper: TrackDataMapper

    @Inject
    lateinit var adapter: TracksAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransition()
        setUpActionBar()
        setUpTrackList()
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(AlbumActivityModule(this))
                .injectTo(this)
    }

    @SuppressLint("NewApi")
    private fun setUpTransition() {
        supportPostponeEnterTransition()
        supportsLollipop { ui.image.transitionName = IMAGE_TRANSITION_NAME }
    }

    private fun setUpTrackList() {
        ui.trackList.adapter = adapter
        ui.trackList.layoutManager = layoutManager
        ui.listCard.translationY = -albumListBreakingEdgeHeight
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = null
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
        presenter.init(getNavigationId())
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun showAlbum(albumDetail: AlbumDetail?) {

        albumDetail?.let(this::updateAlbumDetail)
                ?: postponeTransitions()

    }

    private fun updateAlbumDetail(albumDetail: AlbumDetail) {
        picasso.load(albumDetail.url).fit().centerCrop().into(ui.image, object : Callback.EmptyCallback() {
            override fun onSuccess() {
                makeStatusBarTransparent()
                supportStartPostponedEnterTransition()
                populateTrackList(trackDataMapper.transform(albumDetail.tracks))
                animateTrackListUp()
            }
        })
    }

    private fun postponeTransitions() {
        supportStartPostponedEnterTransition()
        supportFinishAfterTransition()
    }

    private fun animateTrackListUp() {
        ui.listCard.animate().setStartDelay(LIST_ANIMATION_START_DELAY).translationY(NO_TRANSLATION)
    }

    private fun populateTrackList(trackDetails: List<TrackDetail>) {
        adapter.items = trackDetails
    }

    @SuppressLint("InlinedApi")
    private fun makeStatusBarTransparent() {
        supportsLollipop {
            window.setFlags(FLAG_TRANSLUCENT_STATUS, FLAG_TRANSLUCENT_STATUS)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        ui.listCard.animate().alpha(TRANSPARENT)
                .onAnimationEnd { supportFinishAfterTransition() }
    }
}

inline fun ViewPropertyAnimator.onAnimationEnd(
        crossinline continuation: (Animator) -> Unit) {

    setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            continuation(animation)
        }
    })

}