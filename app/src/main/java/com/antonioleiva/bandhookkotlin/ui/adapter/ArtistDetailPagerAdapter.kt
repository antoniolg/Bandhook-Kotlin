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
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

class ArtistDetailPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager)
{
    private val fragments = LinkedHashMap<Fragment, String>()

    override fun getItem(position: Int): Fragment? {
        return fragments.keys.elementAt(position)
    }

    override fun getCount(): Int {
        return  fragments.keys.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments[fragment] = title
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments.values.elementAt(position)
    }
}