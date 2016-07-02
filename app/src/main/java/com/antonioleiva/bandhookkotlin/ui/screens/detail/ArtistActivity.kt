package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.graphics.Palette
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.di.Inject
import com.antonioleiva.bandhookkotlin.di.Injector
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.ArtistDetailPagerAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ArtistDetail
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ArtistDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.ArtistPresenter
import com.antonioleiva.bandhookkotlin.ui.util.getNavigationId
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.ui.view.ArtistView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

/**
 * @author alexey@plainvanillagames.com
 *
 * 01/07/16.
 */

class ArtistActivity: BaseActivity(), ArtistView, Injector by Inject.instance {

    private val picasso by lazy { Picasso.Builder(this).build() }

    override val layoutResource: Int = R.layout.activity_artist

    val image by lazy { find<ImageView>(R.id.collapse_image) }
    val collapsingToolbarLayout by lazy { find<CollapsingToolbarLayout>(R.id.collapse_toolbar) }
    val viewPager by lazy { find<ViewPager>(R.id.viewpager) }
    val tabLayout by lazy { find<TabLayout>(R.id.tabs) }

    val presenter = ArtistPresenter(this, bus, artistDetailInteractorProvider,
            interactorExecutor, ArtistDetailDataMapper())

    val biographyFragment = BiographyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpTransition()
        setUpTopBar()
        setUpViewPager()
        setUpTabLayout()
    }

    private fun setUpTransition() {
        supportPostponeEnterTransition()
        supportsLollipop { image.transitionName = IMAGE_TRANSITION_NAME }
    }

    private fun setUpTopBar() {
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(object : OnTabSelected {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position ?: 0
            }
        })
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

    private fun setUpViewPager() {
        val artistDetailPagerAdapter = ArtistDetailPagerAdapter(supportFragmentManager)
        artistDetailPagerAdapter.addFragment(biographyFragment, "BIO")
        artistDetailPagerAdapter.addFragment(AlbumsFragment(), "DISCOGRAPHY")
        viewPager.adapter = artistDetailPagerAdapter
    }

    override fun showArtist(artistDetail: ArtistDetail) {
        picasso.load(artistDetail.url).fit().centerCrop().into(image, object : Callback.EmptyCallback() {
            override fun onSuccess() {
                makeStatusBarTransparent()
                supportStartPostponedEnterTransition()
                setActionBarTitle(artistDetail.name)
                biographyFragment.setBiographyText(artistDetail.bio)
                setActionBarPalette()
            }
        })
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun makeStatusBarTransparent() {
        supportsLollipop {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private fun setActionBarPalette() {
        val drawable = image.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        Palette.from(bitmap).generate { palette ->
            val darkVibrantColor = palette.getDarkVibrantColor(R.attr.colorPrimary);
            collapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
            collapsingToolbarLayout.setStatusBarScrimColor(darkVibrantColor);
        };
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

interface OnTabSelected: TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }
}
