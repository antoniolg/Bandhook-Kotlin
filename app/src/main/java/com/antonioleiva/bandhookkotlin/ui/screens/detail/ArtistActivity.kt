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

package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.annotation.*
import android.graphics.drawable.*
import android.os.*
import android.support.v7.graphics.*
import android.view.*
import com.antonioleiva.bandhookkotlin.*
import com.antonioleiva.bandhookkotlin.ui.activity.*
import com.antonioleiva.bandhookkotlin.ui.adapter.*
import com.antonioleiva.bandhookkotlin.ui.entity.*
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.*
import com.antonioleiva.bandhookkotlin.ui.fragment.*
import com.antonioleiva.bandhookkotlin.ui.presenter.*
import com.antonioleiva.bandhookkotlin.ui.screens.album.*
import com.antonioleiva.bandhookkotlin.ui.util.*
import com.antonioleiva.bandhookkotlin.ui.view.*
import com.squareup.picasso.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class ArtistActivity : BaseActivity<ArtistLayout>(), ArtistView, AlbumsFragmentContainer, KodeinAware {

    private val _parentKodein by closestKodein()

    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind() from provider {
            ArtistPresenter(this@ArtistActivity, instance(), instance(), instance(), instance(),
                    ArtistDetailDataMapper(), ImageTitleDataMapper())
        }
    }

    override val ui = ArtistLayout()

    val presenter: ArtistPresenter by instance()

    val picasso: Picasso by instance()

    val biographyFragment = BiographyFragment()

    val albumsFragment = AlbumsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpTransition()
        setUpTopBar()
        setUpViewPager()
        setUpTabLayout()
    }

    @SuppressLint("NewApi")
    private fun setUpTransition() {
        supportPostponeEnterTransition()
        supportsLollipop { ui.image.transitionName = IMAGE_TRANSITION_NAME }
    }

    private fun setUpTopBar() {
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpTabLayout() {
        ui.tabLayout.setupWithViewPager(ui.viewPager)
    }

    private fun setUpViewPager() {
        val artistDetailPagerAdapter = ArtistDetailPagerAdapter(supportFragmentManager)
        artistDetailPagerAdapter.addFragment(biographyFragment, resources.getString(R.string.bio_fragment_title))
        artistDetailPagerAdapter.addFragment(albumsFragment, resources.getString(R.string.albums_fragment_title))
        ui.viewPager.adapter = artistDetailPagerAdapter
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

    override fun showArtist(artistDetail: ArtistDetail) {
        picasso.load(artistDetail.url).fit().centerCrop().into(ui.image, object : Callback.EmptyCallback() {
            override fun onSuccess() {
                makeStatusBarTransparent()
                supportStartPostponedEnterTransition()
                setActionBarTitle(artistDetail.name)
                biographyFragment.setBiographyText(artistDetail.bio)
                setActionBarPalette()
            }
        })
    }

    override fun showAlbums(albums: List<ImageTitle>) {
        albumsFragment.showAlbums(albums)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun makeStatusBarTransparent() {
        supportsLollipop {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun setActionBarPalette() {
        val drawable = ui.image.drawable as BitmapDrawable?
        val bitmap = drawable?.bitmap
        if (bitmap != null) {
            Palette.from(bitmap).generate { palette ->
                val darkVibrantColor = palette.getDarkVibrantColor(R.attr.colorPrimary)
                ui.collapsingToolbarLayout.setContentScrimColor(darkVibrantColor)
                ui.collapsingToolbarLayout.setStatusBarScrimColor(darkVibrantColor)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToAlbum(albumId: String) {
        val view = albumsFragment.findViewByItemId(albumId)
        navigate<AlbumActivity>(albumId, view, BaseActivity.IMAGE_TRANSITION_NAME)
    }

    override fun getAlbumsPresenter(): AlbumsPresenter {
        return presenter
    }
}