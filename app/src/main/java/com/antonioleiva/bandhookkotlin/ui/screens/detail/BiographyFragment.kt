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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.ViewAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.util.fromHtml
import com.antonioleiva.bandhookkotlin.ui.util.setTextAppearanceC
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class BiographyFragment : Fragment() {

    private var component: Component? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = container?.let { Component(container) }
        return component?.inflate()
    }

    fun setBiographyText(biographyText: String?) {
        component?.textView?.text = biographyText?.fromHtml()
    }

    fun getBiographyText(): String? {
        return component?.textView?.text?.toString()
    }

    private class Component(override val view: ViewGroup) : ViewAnkoComponent<ViewGroup> {

        lateinit var textView: TextView

        override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
            nestedScrollView {
                textView = textView {
                    backgroundResource = android.R.color.white
                    padding = dip(16)
                    setTextAppearanceC(R.style.TextAppearance_AppCompat_Body1)
                }
            }
        }
    }
}