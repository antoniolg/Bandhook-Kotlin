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
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ViewAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.custom.squareImageView
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.util.loadUrl
import com.antonioleiva.bandhookkotlin.ui.util.setTextAppearanceC
import com.antonioleiva.bandhookkotlin.ui.util.singleClick
import org.jetbrains.anko.*
import kotlin.properties.Delegates

private typealias ClickItemListener = ((ImageTitle) -> Unit)?

class ImageTitleAdapter(var onItemClickListener: ClickItemListener = null)
    : RecyclerView.Adapter<ImageTitleAdapter.ViewHolder>() {

    var items: List<ImageTitle> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ImageTitleComponent(parent), onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun findPositionById(id: String): Int = items.withIndex().first { it.value.id == id }.index

    class ViewHolder(val ui: ImageTitleComponent, val onItemClickListener: ClickItemListener)
        : RecyclerView.ViewHolder(ui.inflate()) {

        fun bind(item: ImageTitle) {
            itemView.singleClick { onItemClickListener?.invoke(item) }
            ui.title.text = item.name
            item.url?.let { ui.image.loadUrl(it) }
        }
    }

    class ImageTitleComponent(override val view: ViewGroup) : ViewAnkoComponent<ViewGroup> {

        lateinit var title: TextView
        lateinit var image: ImageView

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
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
                    }.lparams(width = MATCH_PARENT)

                }.lparams(width = MATCH_PARENT)

            }
        }
    }
}

