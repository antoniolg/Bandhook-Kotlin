package com.antonioleiva.bandhookkotlin.ui.screens.main

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ActivityAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.custom.autoFitRecycler
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class MainLayout : ActivityAnkoComponent<MainActivity> {

    lateinit var recycler: RecyclerView
    override lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        frameLayout {
            recycler = autoFitRecycler {
                topPadding = dip(56)
                clipToPadding = false
                columnWidth = dip(180)

            }.lparams(MATCH_PARENT, MATCH_PARENT)

            toolbar = toolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                backgroundResource = R.color.primary
                fitsSystemWindows = true
                popupTheme = R.style.ThemeOverlay_AppCompat_Light
            }.lparams(width = MATCH_PARENT)
        }
    }
}
