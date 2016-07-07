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
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.util.singleClick
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import kotlin.properties.Delegates

class ImageTitleAdapter() : RecyclerView.Adapter<ImageTitleAdapter.ViewHolder>() {

    var items: List<ImageTitle> by Delegates.observable(emptyList()) { prop, old, new -> notifyDataSetChanged() }

    var onItemClickListener: ((ImageTitle) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.context.layoutInflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder(v, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount() = items.size

    fun findPositionById(id: String): Int = items.withIndex().first({ it.value.id == id }).index

    class ViewHolder(view: View, var onItemClickListener: ((ImageTitle) -> Unit)?) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.find(R.id.title)
        private val image: ImageView = view.find(R.id.image)

        fun setItem(item: ImageTitle) {
            itemView?.singleClick { onItemClickListener?.invoke(item) }
            title.text = item.name
            Picasso.with(itemView.context).load(item.url).centerCrop().fit().into(image)
        }
    }
}

