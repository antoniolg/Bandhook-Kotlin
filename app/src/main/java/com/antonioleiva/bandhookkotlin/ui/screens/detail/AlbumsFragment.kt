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

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.bandhookkotlin.ui.activity.ViewAnkoComponent
import com.antonioleiva.bandhookkotlin.ui.adapter.BaseAdapter
import com.antonioleiva.bandhookkotlin.ui.adapter.ImageTitleAdapter
import com.antonioleiva.bandhookkotlin.ui.custom.AutofitRecyclerView
import com.antonioleiva.bandhookkotlin.ui.custom.autoFitRecycler
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.fragment.AlbumsFragmentContainer
import com.antonioleiva.bandhookkotlin.ui.screens.style
import org.jetbrains.anko.AnkoContext

class AlbumsFragment : Fragment() {

    var albumsFragmentContainer: AlbumsFragmentContainer? = null
        private set

    private var component: Component? = null
    var adapter: ImageTitleAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = container?.let { Component(container) }
        return component?.inflate()?.setup()
    }

    private fun View.setup(): View {
        component?.recycler?.let {
            adapter = ImageTitleAdapter { item ->
                albumsFragmentContainer?.getAlbumsPresenter()?.onAlbumClicked(item)
            }
            it.adapter = adapter
        }
        return this
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is AlbumsFragmentContainer) {
            albumsFragmentContainer = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        albumsFragmentContainer = null
    }

    private class Component(override val view: ViewGroup) : ViewAnkoComponent<ViewGroup> {

        lateinit var recycler: RecyclerView

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            recycler = autoFitRecycler().apply(AutofitRecyclerView::style)
            recycler
        }
    }

    fun findViewByItemId(id: String): View? {
        return adapter?.findPositionById(id)?.let {
            val holder = component?.recycler?.findViewHolderForLayoutPosition(it)
                    as BaseAdapter.BaseViewHolder<ImageTitleAdapter.Component>
            return holder.ui.image
        }
    }

    fun showAlbums(albums: List<ImageTitle>) {
        adapter?.items = albums
    }
}