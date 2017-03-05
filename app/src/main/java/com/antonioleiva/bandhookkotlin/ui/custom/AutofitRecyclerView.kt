/*
 * Copyright (C) 2015 Antonio Leiva
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

package com.antonioleiva.bandhookkotlin.ui.custom

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView
import kotlin.properties.Delegates

/**
 * AutofitRecyclerView was originally explained by Chiu-Ki Chan and explained on its blog:
 * http://blog.sqisland.com/2014/12/recyclerview-autofit-grid.html
 *
 * I just converted to Kotlin code.
 */
class AutofitRecyclerView : RecyclerView {

    private var manager:GridLayoutManager by Delegates.notNull()
    var columnWidth = -1

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet? = null) {
        if (attrs != null){
            val attrsArray = intArrayOf(android.R.attr.columnWidth)
            val ta = context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = ta.getDimensionPixelSize(0, -1)
            ta.recycle()
        }

        manager = GridLayoutManager(context, 1)
        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = Math.max(1, measuredWidth / columnWidth)
            manager.spanCount = spanCount
        }
    }
}

fun ViewManager.autoFitRecycler(theme: Int = 0) = autoFitRecycler(theme) {}
inline fun ViewManager.autoFitRecycler(theme: Int = 0, init: AutofitRecyclerView.() -> Unit) = ankoView(::AutofitRecyclerView, theme, init)
