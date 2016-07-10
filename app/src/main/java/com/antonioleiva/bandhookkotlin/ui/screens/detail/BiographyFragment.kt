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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R

class BiographyFragment: Fragment() {

    private var biographyTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_biography, container, false)

        if (view != null) {
            biographyTextView = view.findViewById(R.id.biography) as TextView
            return view
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun setBiographyText(biographyText: String?) {
        biographyTextView?.text = Html.fromHtml(biographyText)
    }

    fun getBiographyText(): String? {
        return biographyTextView?.text?.toString()
    }
}