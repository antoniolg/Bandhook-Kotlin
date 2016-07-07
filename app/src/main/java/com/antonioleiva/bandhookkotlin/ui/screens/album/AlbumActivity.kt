package com.antonioleiva.bandhookkotlin.ui.screens.album

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.di.Inject
import com.antonioleiva.bandhookkotlin.di.Injector
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.TracksAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.AlbumDetail
import com.antonioleiva.bandhookkotlin.ui.entity.TrackDetail
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.TrackDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumPresenter
import com.antonioleiva.bandhookkotlin.ui.util.getNavigationId
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.ui.view.AlbumView
import com.squareup.picasso.Callback
import org.jetbrains.anko.find

/**
 * @author alexey@plainvanillagames.com
 *
 * 05/07/16.
 */

class AlbumActivity : BaseActivity(), AlbumView, Injector by Inject.instance  {

    override val layoutResource = R.layout.activity_album

    val image by lazy { find<ImageView>(R.id.album_image) }
    val trackList by lazy { find<RecyclerView>(R.id.tracks_list) }
    val listCard by lazy { find<CardView>(R.id.card_view) }
    val albumListBreakingEdgeHeight by lazy { resources.getDimension(R.dimen.album_breaking_edge_height) }
    val trackDataMapper = TrackDataMapper()

    var presenter = AlbumPresenter(this, bus, albumInteractorProvider,
            interactorExecutor, AlbumDetailDataMapper())

    var adapter = TracksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransition()
        setUpActionBar()
        setUpTrackList()
    }

    private fun setUpTransition() {
        supportPostponeEnterTransition()
        supportsLollipop { image.transitionName = IMAGE_TRANSITION_NAME }
    }

    private fun setUpTrackList() {
        trackList.adapter = adapter
        trackList.layoutManager = LinearLayoutManager(this)
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
            supportStartPostponedEnterTransition();
            supportFinishAfterTransition();
        }
    }

    private fun animateTrackListUp() {
        listCard.animate().setStartDelay(500).translationY(0f)
    }

    private fun populateTrackList(TrackDetails: List<TrackDetail>) {
        adapter.items = TrackDetails
    }

    private fun makeStatusBarTransparent() {
        supportsLollipop {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        listCard.animate().alpha(0f).setListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                supportFinishAfterTransition();
            }
        })
    }
}