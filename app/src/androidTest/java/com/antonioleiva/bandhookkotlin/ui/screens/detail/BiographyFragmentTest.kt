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

package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.test.AndroidTestCase
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.mockito.Mockito.*

class BiographyFragmentTest : AndroidTestCase() {

    lateinit var layoutInflater: LayoutInflater
    lateinit var mainView: View
    lateinit var biographyTextView: TextView
    lateinit var biographyFragment: BiographyFragment

    override fun setUp() {
        super.setUp()

        layoutInflater = mock(LayoutInflater::class.java)
        mainView = mock(View::class.java)
        biographyTextView = TextView(context)

        `when`(layoutInflater.inflate(anyInt(), any(ViewGroup::class.java), anyBoolean()))
                .thenReturn(mainView)
        `when`(mainView.findViewById(anyInt())).thenReturn(biographyTextView)

        biographyFragment = BiographyFragment()

        biographyFragment.onCreateView(layoutInflater, null, null)
    }

    fun testBiographyText() {
        // Given
        val htmlText = "<b>bold text</b>"

        // When
        biographyFragment.setBiographyText(htmlText)

        // Then
        assertEquals(Html.fromHtml(htmlText).toString(), biographyFragment.getBiographyText())
    }
}