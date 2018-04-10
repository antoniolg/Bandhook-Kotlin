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

import android.animation.*
import android.annotation.*
import android.os.*
import android.support.v7.widget.*
import android.view.*
import com.antonioleiva.bandhookkotlin.*
import com.antonioleiva.bandhookkotlin.ui.activity.*
import com.antonioleiva.bandhookkotlin.ui.adapter.*
import com.antonioleiva.bandhookkotlin.ui.entity.*
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.*
import com.antonioleiva.bandhookkotlin.ui.presenter.*
import com.antonioleiva.bandhookkotlin.ui.util.*
import com.antonioleiva.bandhookkotlin.ui.view.*
import com.squareup.picasso.*
import org.jetbrains.anko.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class AlbumActivity : BaseActivity<AlbumLayout>(), AlbumView, KodeinAware {

    private val _parentKodein by closestKodein()

    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind() from provider {
            AlbumPresenter(this@AlbumActivity, instance(), instance(), instance(),
                    AlbumDetailDataMapper())

        }

        bind() from provider { TrackDataMapper() }
    }

    override val ui = AlbumLayout()

    companion object {
        private val listAnimationStartDelay = 500L
        private val noTranslation = 0f
        private val transparent = 0f
    }

    val albumListBreakingEdgeHeight by lazy { dimen(R.dimen.album_breaking_edge_height).toFloat() }

    val presenter: AlbumPresenter by instance()

    val trackDataMapper: TrackDataMapper by instance()

    val adapter: TracksAdapter = TracksAdapter()

    val picasso: Picasso by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransition()
        setUpActionBar()
        setUpTrackList()
    }

    @SuppressLint("NewApi")
    private fun setUpTransition() {
        supportPostponeEnterTransition()
        supportsLollipop { ui.image.transitionName = IMAGE_TRANSITION_NAME }
    }

    private fun setUpTrackList() {
        ui.trackList.adapter = adapter
        ui.trackList.layoutManager = LinearLayoutManager(this)
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
        if (albumDetail != null) {
            picasso.load(albumDetail.url).fit().centerCrop().into(ui.image, object : Callback.EmptyCallback() {
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
        ui.listCard.animate().setStartDelay(listAnimationStartDelay).translationY(noTranslation)
    }

    private fun populateTrackList(trackDetails: List<TrackDetail>) {
        adapter.items = trackDetails
    }

    @SuppressLint("InlinedApi")
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
        ui.listCard.animate().alpha(transparent).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                supportFinishAfterTransition()
            }
        })
    }
}