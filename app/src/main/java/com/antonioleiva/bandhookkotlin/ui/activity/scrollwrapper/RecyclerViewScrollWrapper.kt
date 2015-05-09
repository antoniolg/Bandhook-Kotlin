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

package com.antonioleiva.bandhookkotlin.ui.activity.scrollwrapper

import android.support.v7.widget.RecyclerView
import java.util.ArrayList

class RecyclerViewScrollWrapper(recyclerView: RecyclerView) : ScrollWrapper {

    override var scrollX: Int = 0
    override var scrollY: Int = 0
    override var dX: Int = 0
    override var dY: Int = 0
    override var scrollObservers: MutableList<((ScrollWrapper) -> Unit)> = ArrayList()

    init {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                scrollX += dx
                scrollY += dy
                dX = dx
                dY = dy
                scrollObservers forEach { it.invoke(this@RecyclerViewScrollWrapper)}
            }
        })
    }
}