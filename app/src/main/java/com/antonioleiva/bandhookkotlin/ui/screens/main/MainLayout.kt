package com.antonioleiva.bandhookkotlin.ui.screens.main

import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ActivityAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.custom.AutofitRecyclerView
import com.antonioleiva.bandhookkotlin.ui.custom.autoFitRecycler
import com.antonioleiva.bandhookkotlin.ui.screens.style
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.matchParent

class MainLayout : ActivityAnkoComponent<MainActivity> {

    lateinit var recycler: RecyclerView
    override lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

        coordinatorLayout {

            appBarLayout {
                toolbar = themedToolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                    backgroundResource = R.color.primary
                }.lparams(width = matchParent) {
                    scrollFlags = SCROLL_FLAG_SNAP or SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams(width = matchParent)

            recycler = autoFitRecycler()
                    .apply(AutofitRecyclerView::style)
                    .lparams(matchParent, matchParent) {
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
        }
    }
}