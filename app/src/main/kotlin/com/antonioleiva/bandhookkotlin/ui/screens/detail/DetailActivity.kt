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

package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.Inject
import com.antonioleiva.bandhookkotlin.Injector
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.activity.ScrollableHeaderActivity
import com.antonioleiva.bandhookkotlin.ui.entity.ArtistDetail
import com.antonioleiva.bandhookkotlin.ui.entity.mapper.ArtistDetailDataMapper
import com.antonioleiva.bandhookkotlin.ui.presenter.DetailPresenter
import com.antonioleiva.bandhookkotlin.ui.util.bindView
import com.antonioleiva.bandhookkotlin.ui.util.getNavigationId
import com.antonioleiva.bandhookkotlin.ui.util.supportsLollipop
import com.antonioleiva.bandhookkotlin.ui.view.DetailView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.anko.text

class DetailActivity : BaseActivity(), DetailView, ScrollableHeaderActivity,
        Injector by Inject.instance {

    override val layoutResource: Int = R.layout.activity_detail
    val presenter = DetailPresenter(this, bus, artistDetailInteractorProvider,
            interactorExecutor, ArtistDetailDataMapper())

    val image: ImageView by bindView(R.id.image)
    val name: TextView by bindView(R.id.name)
    val biography: TextView by bindView(R.id.biography)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        supportPostponeEnterTransition()
        setTitle(null)

        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        supportsLollipop { image.setTransitionName(BaseActivity.IMAGE_TRANSITION_NAME) }
    }

    override fun onResume() {
        super<BaseActivity>.onResume()
        presenter.onResume()
        presenter.init(getNavigationId())
    }

    override fun onPause() {
        super<BaseActivity>.onPause()
        presenter.onPause()
    }

    override fun showArtist(artistDetail: ArtistDetail) {
        name.text = artistDetail.name
        biography.text = Html.fromHtml(artistDetail.bio)

        Picasso.with(this).load(artistDetail.url).fit().centerCrop().into(image, object : Callback.EmptyCallback() {
            override fun onSuccess() {
                initScrollableHeader()
                supportStartPostponedEnterTransition()
            }
        })
    }
}