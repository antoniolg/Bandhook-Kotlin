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

import android.support.v7.widget.Toolbar
import com.antonioleiva.bandhookkotlin.ui.activity.scrollwrapper.ScrollWrapper
import com.antonioleiva.bandhookkotlin.ui.util.animateEnter
import com.antonioleiva.bandhookkotlin.ui.util.animateExit

interface HidingToolbarActivity {

    val toolbar: Toolbar

    fun initHidingToolbar(viewWrapper: ScrollWrapper) {

        var hidden = false

        viewWrapper.scrollObservers.add { wrapper ->
            if (wrapper.dY > 0 && wrapper.scrollY > toolbar.height && !hidden) {
                hidden = true
                toolbar.animateExit()
            } else if (wrapper.dY < 0 && hidden) {
                hidden = false
                toolbar.animateEnter()
            }
        }

    }
}

