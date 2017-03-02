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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.ui.entity.TrackDetail
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TracksAdapterTest {

    @Mock
    lateinit var viewHolder: TracksAdapter.ViewHolder
    @Mock
    lateinit var viewGroup: ViewGroup
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var layoutInflater: LayoutInflater
    @Mock
    lateinit var view: View
    @Mock
    lateinit var textView: TextView

    lateinit var trackDetail: TrackDetail

    lateinit var items: List<TrackDetail>

    lateinit var trackAdapter: SpiedTracksAdapter

    @Before
    fun setUp() {

        `when`(viewGroup.context).thenReturn(context)
        `when`(context.getSystemService(anyString())).thenReturn(layoutInflater)
        `when`(layoutInflater.inflate(anyInt(), eq(viewGroup), anyBoolean())).thenReturn(view)
        `when`(view.findViewById(anyInt())).thenReturn(textView)

        trackDetail = TrackDetail("track name", 69)

        items = listOf(trackDetail, trackDetail)

        trackAdapter = SpiedTracksAdapter()
    }

    @Test
    fun testSetItems() {
        // Given
        assertFalse(trackAdapter.notified)

        // When
        trackAdapter.items = items

        // Then
        assertTrue(trackAdapter.notified)
    }

    @Test
    fun testGetItemCount() {
        // When
        trackAdapter.items = items

        // Then
        assertEquals(items.size, trackAdapter.itemCount)
    }

    @Test
    fun testOnBindViewHolder() {
        // Given
        trackAdapter.items = items

        // When
        trackAdapter.onBindViewHolder(viewHolder, 0)

        // Then
        verify(viewHolder).setItem(items[0], 0)

    }

    @Test
    fun testOnCreateViewHolder() {
        // When
        val createdViewHolder = trackAdapter.onCreateViewHolder(viewGroup, 0)

        // Then
        assertTrue(createdViewHolder is TracksAdapter.ViewHolder)
    }

    @Test
    fun testViewHolderSetItem() {
        // Given
        val viewHolder = TracksAdapter.ViewHolder(view)

        // When
        viewHolder.setItem(trackDetail, 0)

        // Then
        verify(textView).text = "1"
        verify(textView).text = trackDetail.name
        verify(textView).text = "1:09"
    }
}

class SpiedTracksAdapter() : TracksAdapter() {
    var notified = false

    override fun notifyDataSetChange() {
        notified = true
    }
}