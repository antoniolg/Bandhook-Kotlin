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

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArtistDetailPagerAdapterTest {

    private val title = "title"

    @Mock
    lateinit var fragmentManager: FragmentManager
    @Mock
    lateinit var fragment: Fragment
    @Mock
    lateinit var anotherFragment: Fragment

    lateinit var artistDetailPagerAdapter: ArtistDetailPagerAdapter

    @org.junit.Before
    fun setUp() {
        artistDetailPagerAdapter = ArtistDetailPagerAdapter(fragmentManager)
    }

    @Test
    fun testAddFragment() {
        // When
        artistDetailPagerAdapter.addFragment(fragment, title)

        // Then
        assertEquals(title, artistDetailPagerAdapter.fragments[fragment])
    }

    @Test
    fun testGetItem() {
        // Given
        assertEquals(0, artistDetailPagerAdapter.count)

        // When
        artistDetailPagerAdapter.addFragment(fragment, title)

        // Then
        assertEquals(fragment, artistDetailPagerAdapter.getItem(0))
    }

    @Test
    fun testGetCount() {
        // When
        artistDetailPagerAdapter.addFragment(fragment, title)
        artistDetailPagerAdapter.addFragment(anotherFragment, title)

        // Then
        assertEquals(2, artistDetailPagerAdapter.count)
    }

    @Test
    fun testGetPageTitle() {
        // When
        artistDetailPagerAdapter.addFragment(fragment, title)

        // Then
        assertEquals(title, artistDetailPagerAdapter.getPageTitle(0))
    }
}