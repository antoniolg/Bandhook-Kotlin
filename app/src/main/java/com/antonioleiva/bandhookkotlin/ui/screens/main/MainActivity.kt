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

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.di.Inject
import com.antonioleiva.bandhookkotlin.di.Injector
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.activity.HidingToolbarActivity
import com.antonioleiva.bandhookkotlin.ui.activity.scrollwrapper.RecyclerViewScrollWrapper
import com.antonioleiva.bandhookkotlin.ui.adapter.ImageTitleAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ImageTitleDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.MainPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.detail.ArtistActivity
import com.antonioleiva.bandhookkotlin.ui.util.navigate
import com.antonioleiva.bandhookkotlin.ui.view.MainView
import org.jetbrains.anko.find
import org.jetbrains.anko.findOptional

class MainActivity : BaseActivity(), MainView, HidingToolbarActivity, Injector by Inject.instance {

    override val layoutResource = R.layout.activity_main

    val adapter = ImageTitleAdapter()
    val presenter = MainPresenter(this, bus, recommendedArtistsInteractorProvider,
            interactorExecutor, ImageTitleDataMapper())

    val recycler by lazy { find<RecyclerView>(R.id.recycler) }
    val background by lazy { findOptional<View>(R.id.background) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scrollWrapper = RecyclerViewScrollWrapper(recycler)
        init(scrollWrapper)
        initHidingToolbar(scrollWrapper)
    }

    fun init(scrollWrapper: RecyclerViewScrollWrapper) {
        recycler.adapter = adapter
        adapter.onItemClickListener = { presenter.onArtistClicked(it) }
        scrollWrapper.scrollObservers.add { wrapper ->
            background?.translationY = (-wrapper.scrollY / 2).toFloat()
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return onOptionsItemSelected(item)
        }
    }

    override fun navigateToDetail(id: String) {
        navigate<ArtistActivity>(id, findItemById(id), BaseActivity.IMAGE_TRANSITION_NAME)
    }

    private fun findItemById(id: String): View {
        val pos = adapter.findPositionById(id)
        return recycler.layoutManager.findViewByPosition(pos).findViewById(R.id.image)
    }
}