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

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.graphics.Palette
import android.view.MenuItem
import android.view.WindowManager
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.di.ApplicationComponent
import com.antonioleiva.bandhookkotlin.di.subcomponent.detail.ArtistActivityModule
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.ArtistDetailPagerAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ArtistDetail
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.fragment.AlbumsFragmentContainer
import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumsPresenter
import com.antonioleiva.bandhookkotlin.ui.presenter.ArtistPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.album.AlbumActivity
import com.antonioleiva.bandhookkotlin.ui.util.getNavigationId
import com.antonioleiva.bandhookkotlin.ui.util.navigate
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.ui.view.ArtistView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ArtistActivity : BaseActivity<ArtistLayout>(), ArtistView, AlbumsFragmentContainer {

    override val ui = ArtistLayout()

    @Inject @VisibleForTesting
    lateinit var presenter: ArtistPresenter

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var biographyFragment: BiographyFragment

    @Inject
    lateinit var albumsFragment: AlbumsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpTransition()
        setUpTopBar()
        setUpViewPager()
        setUpTabLayout()
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(ArtistActivityModule(this))
                .injectTo(this)
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
