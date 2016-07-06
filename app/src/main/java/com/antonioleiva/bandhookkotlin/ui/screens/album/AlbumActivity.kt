package com.antonioleiva.bandhookkotlin.ui.screens.album

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.di.Inject
import com.antonioleiva.bandhookkotlin.di.Injector
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.entity.AlbumDetail
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.AlbumDetailDataMapper
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
    val text by lazy { find<TextView>(R.id.albums) }

    var presenter = AlbumPresenter(this, bus, albumInteractorProvider,
            interactorExecutor, AlbumDetailDataMapper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportPostponeEnterTransition()
        supportsLollipop { image.transitionName = IMAGE_TRANSITION_NAME }
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
                    supportStartPostponedEnterTransition()

                    val tracks = albumDetail.tracks.map { it.name }.joinToString()
                    text.text = tracks
                }
            })
        } else {
            supportStartPostponedEnterTransition();
            supportFinishAfterTransition();
        }
    }
}