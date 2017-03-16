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

package com.antonioleiva.bandhookkotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ViewAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.custom.squareImageView
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.util.loadUrl
import com.antonioleiva.bandhookkotlin.ui.util.setTextAppearanceC
import org.jetbrains.anko.*

class ImageTitleAdapter(listener: (ImageTitle) -> Unit)
    : BaseAdapter<ImageTitle, ImageTitleAdapter.Component>(listener) {

    override val bind: Component.(item: ImageTitle) -> Unit = { item ->
        title.text = item.name
        item.url?.let { image.loadUrl(it) }
    }

    override fun onCreateComponent(parent: RecyclerView) = Component(parent)

    fun findPositionById(id: String): Int = items.withIndex().first { it.value.id == id }.index

    class Component(override val view: RecyclerView) : ViewAnkoComponent<RecyclerView> {

        lateinit var title: TextView
        lateinit var image: ImageView

        override fun createView(ui: AnkoContext<RecyclerView>) = with(ui) {
            frameLayout {

                verticalLayout {

                    image = squareImageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        backgroundResource = R.color.cardview_dark_background
                    }
                    title = textView {
                        padding = dip(16)
                        backgroundResource = R.color.cardview_dark_background
                        setTextAppearanceC(R.style.TextAppearance_AppCompat_Subhead_Inverse)
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(width = matchParent)

                }.lparams(width = matchParent)

            }
        }
    }
}

