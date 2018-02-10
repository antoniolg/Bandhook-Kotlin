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

package com.antonioleiva.bandhookkotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.antonioleiva.bandhookkotlin.App
import com.antonioleiva.bandhookkotlin.di.ApplicationComponent
import org.jetbrains.anko.setContentView

abstract class BaseActivity<out UI : ActivityAnkoComponent<out AppCompatActivity>> : AppCompatActivity() {

    companion object {
        const val IMAGE_TRANSITION_NAME = "activity_image_transition"
    }

    abstract val ui: UI

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(App.graph)
        (ui as ActivityAnkoComponent<AppCompatActivity>).setContentView(this)
        setSupportActionBar(ui.toolbar)
    }

    abstract fun injectDependencies(applicationComponent: ApplicationComponent)
}