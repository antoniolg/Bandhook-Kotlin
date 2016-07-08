/*
 * Copyright (C) 2016 Alexey Verein
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

import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.entity.TrackDetail
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import kotlin.properties.Delegates

open class TracksAdapter() : RecyclerView.Adapter<TracksAdapter.ViewHolder>() {

    var items: List<TrackDetail> by Delegates.observable(emptyList())
                    { prop, old, new -> notifyDataSetChange() }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val v = parent.context.layoutInflater.inflate(R.layout.track_item_view, parent, false)
        return TracksAdapter.ViewHolder(v)
    }

    @VisibleForTesting
    open fun notifyDataSetChange() {
        notifyDataSetChanged()
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val trackNumberTextView: TextView = view.find(R.id.track_number)
        private val trackNameTextView: TextView = view.find(R.id.track_name)
        private val trackLengthTextView: TextView = view.find(R.id.track_length)

        open fun setItem(item: TrackDetail, position: Int) {

            trackNumberTextView.text = "${position + 1}"
            trackNameTextView.text = item.name
            trackLengthTextView.text = "${secondsToTrackDurationString(item)}"
        }

        private fun secondsToTrackDurationString(item: TrackDetail): String {
            val fullMinutes = item.duration / 60
            val restSeconds = item.duration % 60
            val trackLength = String.format("%d:%02d", fullMinutes, restSeconds)
            return trackLength
        }
    }
}