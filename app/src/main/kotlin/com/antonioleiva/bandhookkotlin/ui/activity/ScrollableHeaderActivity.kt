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
import com.antonioleiva.bandhookkotlin.ui.util.findView
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.util.with

trait ScrollableHeaderActivity : BaseActivity {

    fun initScrollableHeader() {
        val titleText: TextView = findView(R.id.name)
        val headerImage: ImageView = findView(R.id.image)
        val scrollableView: ObservableScrollView = findView(R.id.scrollableView)

        val minHeight = getToolbarHeight() + getResources().getDimensionPixelOffset(R.dimen.statusbar_height)
        val maxHeight = getResources().getDimensionPixelOffset(R.dimen.detail_toolbar_height);

        initWindow()
        initTitle(titleText, minHeight, maxHeight, scrollableView.getScrollY())
        initToolbar(headerImage, minHeight, maxHeight, scrollableView.getScrollY())

        scrollableView.listener = { x, y ->
            updateTitleScale(titleText, minHeight, maxHeight, y)
            updateToolbarAlpha(minHeight, maxHeight, y)
            updateImageTranslation(headerImage, y)
        }
    }

    private fun getToolbarHeight(): Int{
        val a = getTheme().obtainStyledAttributes(R.style.AppTheme, intArray(R.attr.actionBarSize));
        val attributeResourceId = a.getResourceId(0, 0);
        a.recycle()
        return getResources().getDimensionPixelOffset(attributeResourceId)
    }

    private fun updateImageTranslation(image: ImageView, scrollY: Int) {
        image.setTranslationY(-(scrollY / 2).toFloat())
    }

    private fun initWindow() {
        supportsLollipop {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private fun initTitle(titleText: TextView, minHeight: Int, maxHeight: Int, scrollY: Int) {
        with(titleText) {
            setPivotX(0f)
            setPivotY(getHeight().toFloat())
            updateTitleScale(this, minHeight, maxHeight, scrollY)
        }
    }

    private fun initToolbar(headerImage: ImageView, minHeight: Int, maxHeight: Int, scrollY: Int) {
        val drawable = headerImage.getDrawable() as BitmapDrawable
        val bitmap = drawable.getBitmap()

        val lp = toolbar.getLayoutParams()
        lp.height = maxHeight
        toolbar.setLayoutParams(lp)

        var toolbarColor = Color.DKGRAY
        Palette.generateAsync(bitmap) { palette ->
            toolbarColor = palette.getDarkVibrantColor(toolbarColor)
            toolbar.setBackgroundColor(toolbarColor)
            updateToolbarAlpha(minHeight, maxHeight, scrollY)
        }
    }

    private fun updateTitleScale(titleText: TextView, minHeight: Int, maxHeight: Int, scrollY: Int) {
        val current = maxHeight - minHeight - scrollY
        val scale = 1f + (current.toFloat() / maxHeight.toFloat())
        titleText.setScaleX(Math.max(1f, scale))
        titleText.setScaleY(Math.max(1f, scale))
    }

    private fun updateToolbarAlpha(minHeight: Int, maxHeight: Int, y: Int) {
        val alpha = ((y / (maxHeight - minHeight).toFloat()) * 255).toInt()
        val finalAlpha = Math.max(0, Math.min(alpha, 255))

        val toolbarParams = toolbar.getLayoutParams()
        toolbarParams.height = Math.max(maxHeight - y, minHeight)
        toolbar.setLayoutParams(toolbarParams)
        toolbar.getBackground().setAlpha(finalAlpha)
    }
}
