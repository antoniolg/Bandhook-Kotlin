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
import android.view.View
import com.antonioleiva.bandhookkotlin.di.ApplicationComponent
import com.antonioleiva.bandhookkotlin.di.subcomponent.main.MainActivityModule
import com.antonioleiva.bandhookkotlin.ui.activity.BaseActivity
import com.antonioleiva.bandhookkotlin.ui.adapter.BaseAdapter
import com.antonioleiva.bandhookkotlin.ui.adapter.ImageTitleAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle
import com.antonioleiva.bandhookkotlin.ui.presenter.MainPresenter
import com.antonioleiva.bandhookkotlin.ui.screens.detail.ArtistActivity
import com.antonioleiva.bandhookkotlin.ui.util.navigate
import com.antonioleiva.bandhookkotlin.ui.view.MainView
import com.github.finecinnamon.NonEmptyList
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : BaseActivity<MainLayout>(), MainView {

    override val ui = MainLayout()

    @Inject
    lateinit var presenter: MainPresenter

    val adapter = ImageTitleAdapter { presenter.onArtistClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.recycler.adapter = adapter
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(MainActivityModule(this))
                .injectTo(this)
    }

    override fun showUnhandledException(e: Exception) {
        //TODO show unhandled exceptions
    }

    override fun onResume() {
        super.onResume()

        launch(job!! + UI) {
            try {
                presenter.onResume()

                // TODO: Delete the delay and the toasts. They are only included now to illustrate
                // that the main thread is not blocked due to pending tasks and that cleanup occurs
                // automatically in both of the following scenarios
                // 1) This block of code ends
                // 2) The activity is suspended before 1) occurs (try rotating the phone)
                delay(10000)
                toast("All tasks finished")
            } finally {
                toast("Cleaning up")
                presenter.onPause()
            }
        }
    }

    override fun showArtists(artists: NonEmptyList<ImageTitle>) = runOnUiThread {
        adapter.items = artists.all
    }

    override fun navigateToDetail(id: String) {
        navigate<ArtistActivity>(id, findItemById(id), BaseActivity.IMAGE_TRANSITION_NAME)
    }

    private fun findItemById(id: String): View {
        val pos = adapter.findPositionById(id)
        val holder = ui.recycler.findViewHolderForLayoutPosition(pos)
                as BaseAdapter.BaseViewHolder<ImageTitleAdapter.Component>
        return holder.ui.image
    }
}
