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

package com.antonioleiva.bandhookkotlin.ui.screens.main

import android.os.*
import android.view.*
import com.antonioleiva.bandhookkotlin.ui.activity.*
import com.antonioleiva.bandhookkotlin.ui.adapter.*
import com.antonioleiva.bandhookkotlin.ui.entity.*
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.*
import com.antonioleiva.bandhookkotlin.ui.presenter.*
import com.antonioleiva.bandhookkotlin.ui.screens.detail.*
import com.antonioleiva.bandhookkotlin.ui.util.*
import com.antonioleiva.bandhookkotlin.ui.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MainActivity : BaseActivity<MainLayout>(), MainView, KodeinAware {

    private val _parentKodein by closestKodein()

    override val kodein: Kodein = Kodein {
        extend(_parentKodein)
        bind() from provider {
            MainPresenter(this@MainActivity, instance(), instance(), instance(),
                    ImageTitleDataMapper())
        }
    }

    override val ui = MainLayout()

    val presenter: MainPresenter by instance()

    val adapter = ImageTitleAdapter { presenter.onArtistClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun showArtists(artists: List<ImageTitle>) {
        adapter.items = artists
    }

    override fun navigateToDetail(id: String) {
        navigate<ArtistActivity>(id, findItemById(id), BaseActivity.IMAGE_TRANSITION_NAME)
    }

    @Suppress("UNCHECKED_CAST")
    private fun findItemById(id: String): View {
        val pos = adapter.findPositionById(id)
        val holder = ui.recycler.findViewHolderForLayoutPosition(pos)
                as BaseAdapter.BaseViewHolder<ImageTitleAdapter.Component>
        return holder.ui.image
    }
}