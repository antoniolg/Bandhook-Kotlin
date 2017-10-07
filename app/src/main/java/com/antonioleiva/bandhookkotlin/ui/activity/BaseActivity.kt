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

import android.os.*
import android.support.v7.app.*
import com.antonioleiva.bandhookkotlin.*
import com.github.salomonbrys.kodein.*
import org.jetbrains.anko.*

abstract class BaseActivity<out UI : ActivityAnkoComponent<out AppCompatActivity>> : AppCompatActivity(), KodeinInjected {

    companion object {
        val IMAGE_TRANSITION_NAME = "activity_image_transition"
    }

    abstract val ui: UI

    override val injector = KodeinInjector()

    val kodein by Kodein.lazy {
        extend(applicationContext.asApp().kodein)
        import(activityModule)
    }

    abstract val activityModule: Kodein.Module

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        inject(kodein)
        super.onCreate(savedInstanceState)
        (ui as ActivityAnkoComponent<AppCompatActivity>).setContentView(this)
        setSupportActionBar(ui.toolbar)
    }
}