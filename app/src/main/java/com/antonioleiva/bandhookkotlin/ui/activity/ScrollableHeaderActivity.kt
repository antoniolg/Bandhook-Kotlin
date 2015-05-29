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

package com.antonioleiva.bandhookkotlin.ui.activity

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.custom.ObservableScrollView
import com.antonioleiva.bandhookkotlin.ui.util.getAttrId
import com.antonioleiva.bandhookkotlin.ui.util.getDimen
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.util.with
import org.jetbrains.anko.*

interface ScrollableHeaderActivity : BaseActivity {

    fun initScrollableHeader() {
        val titleText: TextView = find(R.id.name)
        val headerImage: ImageView = find(R.id.image)
        val scrollView: ObservableScrollView = find(R.id.scrollableView)

        val minHeight = getToolbarHeight() + getDimen(R.dimen.statusbar_height)
        val maxHeight = getDimen(R.dimen.detail_toolbar_height);

        initWindow()
        initTitle(titleText, minHeight, maxHeight, scrollView.getScrollY())
        initToolbar(headerImage, minHeight, maxHeight, scrollView.getScrollY())

        scrollView.listener = { x, y ->
            updateTitleScale(titleText, minHeight, maxHeight, y)
            updateToolbarAlpha(minHeight, maxHeight, y)
            updateImageTranslation(headerImage, y)
        }
    }

    private fun getToolbarHeight() = getDimen(getAttrId(R.style.AppTheme, R.attr.actionBarSize))

    private fun updateImageTranslation(image: ImageView, scrollY: Int) {
        image.translationY = -(scrollY / 2).toFloat()
    }

    private fun initWindow() {
        supportsLollipop {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private fun initTitle(titleText: TextView, minHeight: Int, maxHeight: Int, scrollY: Int) {
        with(titleText) {
            pivotX = 0f
            pivotY = getHeight().toFloat()
            updateTitleScale(this, minHeight, maxHeight, scrollY)
        }
    }

    private fun initToolbar(headerImage: ImageView, minHeight: Int, maxHeight: Int, scrollY: Int) {
        val drawable = headerImage.getDrawable() as BitmapDrawable
        val bitmap = drawable.getBitmap()

        toolbar.layoutParams?.height = maxHeight

        var toolbarColor = Color.DKGRAY
        Palette.Builder(bitmap).generate { palette ->
            toolbarColor = palette.getDarkVibrantColor(toolbarColor)
            toolbar.setBackgroundColor(toolbarColor)
            updateToolbarAlpha(minHeight, maxHeight, scrollY)
        }
    }

    private fun updateTitleScale(titleText: TextView, minHeight: Int, maxHeight: Int, scrollY: Int) {
        val current = maxHeight - minHeight - scrollY
        val scale = 1f + (current.toFloat() / maxHeight.toFloat())
        titleText.scaleX = Math.max(1f, scale)
        titleText.scaleY = Math.max(1f, scale)
    }

    private fun updateToolbarAlpha(minHeight: Int, maxHeight: Int, y: Int) {
        val alpha = ((y / (maxHeight - minHeight).toFloat()) * 255).toInt()
        val finalAlpha = Math.max(0, Math.min(alpha, 255))

        with(toolbar){
            val toolbarParams = layoutParams
            toolbarParams?.height = Math.max(maxHeight - y, minHeight)
            layoutParams = toolbarParams
            getBackground().setAlpha(finalAlpha)
        }
    }
}
