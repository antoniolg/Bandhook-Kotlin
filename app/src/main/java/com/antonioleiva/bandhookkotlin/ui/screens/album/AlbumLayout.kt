package com.antonioleiva.bandhookkotlin.ui.screens.album

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ActivityAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.custom.squareImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class AlbumLayout : ActivityAnkoComponent<AlbumActivity> {

    override lateinit var toolbar: Toolbar
    lateinit var image: ImageView
    lateinit var trackList: RecyclerView
    lateinit var listCard: CardView

    override fun createView(ui: AnkoContext<AlbumActivity>) = with(ui) {
        relativeLayout {

            image = squareImageView {
                id = View.generateViewId()
                backgroundResource = android.R.color.darker_gray
            }.lparams(width = matchParent)

            toolbar = themedToolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar)
                    .lparams(width = matchParent) {
                        topMargin = dimen(R.dimen.statusbar_height)
                    }

            listCard = cardView {
                radius = dip(2).toFloat()
                cardElevation = dip(5).toFloat()

                trackList = recyclerView()

            }.lparams {
                below(image)
                horizontalMargin = dip(8)
                topMargin = dimen(R.dimen.album_breaking_edge_height)
                bottomMargin = dip(-5)
            }
        }
    }

}