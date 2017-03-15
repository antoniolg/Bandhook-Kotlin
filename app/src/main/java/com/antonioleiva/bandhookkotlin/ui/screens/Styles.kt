package com.antonioleiva.bandhookkotlin.ui.screens

import android.view.View
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.custom.AutofitRecyclerView
import com.antonioleiva.bandhookkotlin.ui.custom.PaddingItemDecoration
import org.jetbrains.anko.*


fun AutofitRecyclerView.style() {
    clipToPadding = false
    columnWidth = dimen(R.dimen.column_width)
    scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
    horizontalPadding = dimen(R.dimen.recycler_spacing)
    verticalPadding = dip(2)
    addItemDecoration(PaddingItemDecoration(dip(2)))
}