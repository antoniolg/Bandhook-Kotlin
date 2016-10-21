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
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
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
import org.jetbrains.anko.find
import javax.inject.Inject

class AlbumActivity : BaseActivity(), AlbumView {

    companion object {
        private val listAnimationStartDelay = 500L
        private val noTranslation = 0f
        private val transparent = 0f
    }

    override val layoutResource = R.layout.activity_album
    val image by lazy { find<ImageView>(R.id.album_image) }
    val trackList by lazy { find<RecyclerView>(R.id.tracks_list) }
    val listCard by lazy { find<CardView>(R.id.card_view) }
    val albumListBreakingEdgeHeight by lazy { resources.getDimension(R.dimen.album_breaking_edge_height) }

    @Inject @VisibleForTesting
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

    private fun setUpTransition() {
        supportPostponeEnterTransition()
        supportsLollipop { image.transitionName = IMAGE_TRANSITION_NAME }
    }

    private fun setUpTrackList() {
        trackList.adapter = adapter
        trackList.layoutManager = layoutManager
        listCard.translationY = -albumListBreakingEdgeHeight
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
        if (albumDetail != null) {
            picasso.load(albumDetail.url).fit().centerCrop().into(image, object : Callback.EmptyCallback() {
                override fun onSuccess() {
                    makeStatusBarTransparent()
                    supportStartPostponedEnterTransition()
                    populateTrackList(trackDataMapper.transform(albumDetail.tracks))
                    animateTrackListUp()
                }
            })
        } else {
            supportStartPostponedEnterTransition()
            supportFinishAfterTransition()
        }
    }

    private fun animateTrackListUp() {
        listCard.animate().setStartDelay(listAnimationStartDelay).translationY(noTranslation)
    }

    private fun populateTrackList(trackDetails: List<TrackDetail>) {
        adapter.items = trackDetails
    }

    private fun makeStatusBarTransparent() {
        supportsLollipop {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
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
        listCard.animate().alpha(transparent).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                supportFinishAfterTransition()
            }
        })
    }
}