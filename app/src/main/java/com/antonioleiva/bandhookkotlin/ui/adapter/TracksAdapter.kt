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
import com.antonioleiva.bandhookkotlin.ui.util.inflate
import org.jetbrains.anko.find
import kotlin.properties.Delegates

open class TracksAdapter : RecyclerView.Adapter<TracksAdapter.ViewHolder>() {

    var items: List<TrackDetail> by Delegates.observable(emptyList())
                    { _, _, _ -> notifyDataSetChange() }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val v = parent.inflate(R.layout.track_item_view)
        return TracksAdapter.ViewHolder(v)
    }

    @VisibleForTesting
    open fun notifyDataSetChange() {
        notifyDataSetChanged()
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val timeStampPattern = "%d:%02d"
        private val timeSystemBaseNumber = 60

        private val trackNumberTextView: TextView = view.find(R.id.track_number)
        private val trackNameTextView: TextView = view.find(R.id.track_name)
        private val trackLengthTextView: TextView = view.find(R.id.track_length)

        open fun setItem(item: TrackDetail, position: Int) {

            trackNumberTextView.text = "${position + 1}"
            trackNameTextView.text = item.name
            trackLengthTextView.text = secondsToTrackDurationString(item)
        }

        private fun secondsToTrackDurationString(item: TrackDetail): String {
            val fullMinutes = item.duration / timeSystemBaseNumber
            val restSeconds = item.duration % timeSystemBaseNumber
            val trackLength = String.format(timeStampPattern, fullMinutes, restSeconds)
            return trackLength
        }
    }
}
