package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ActivityAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.custom.squareImageView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.viewPager

class ArtistLayout : ActivityAnkoComponent<ArtistActivity> {

    override lateinit var toolbar: Toolbar

    lateinit var image: ImageView
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun createView(ui: AnkoContext<ArtistActivity>) = with(ui) {

        coordinatorLayout {
            fitsSystemWindows = true

            appBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                fitsSystemWindows = true

                collapsingToolbarLayout = collapsingToolbarLayout {
                    fitsSystemWindows = true
                    collapsedTitleGravity = Gravity.TOP
                    expandedTitleMarginBottom = dip(60)

                    image = squareImageView {
                        fitsSystemWindows = true
                    }.lparamsC(MATCH_PARENT) {
                        collapseMode = COLLAPSE_MODE_PARALLAX
                    }

                    toolbar = toolbar {
                        popupTheme = R.style.ThemeOverlay_AppCompat_Light
                        gravity = Gravity.TOP
                        titleMarginTop = dip(16)
                    }.lparamsC(width = MATCH_PARENT, height = dip(88)) {
                        collapseMode = COLLAPSE_MODE_PIN
                    }

                    tabLayout = tabLayout {
                        setSelectedTabIndicatorColor(Color.WHITE)
                    }.lparamsC(width = MATCH_PARENT) {
                        gravity = Gravity.BOTTOM
                    }

                }.lparams(width = MATCH_PARENT) {
                    scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

            }.lparams(width = MATCH_PARENT)

            viewPager = viewPager {
                id = View.generateViewId()
            }.lparams {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}

/**
 * For some reason, the regular lparams is returning FrameLayout LayoutParams, instead of the ones
 * for CollapsingToolbarLayout. This fixes it.
 */
private fun <T : android.view.View> T.lparamsC(width: kotlin.Int = WRAP_CONTENT, height: kotlin.Int = WRAP_CONTENT, init: CollapsingToolbarLayout.LayoutParams.() -> kotlin.Unit = {}): T {
    val params = CollapsingToolbarLayout.LayoutParams(width, height)
    params.init()
    layoutParams = params
    return this
}