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

/**
 * A wrapper to make easier listening to a scrollable view. It will give information about the
 * scroll position as well as the difference from latest listener call. This way, classes that use
 * it don´t need to know which type of view are they dealing with.
 */
trait ScrollWrapper {
    var scrollX: Int
    var scrollY: Int
    var dX: Int
    var dY: Int
    var scrollListener: ((viewWrapper: ScrollWrapper) -> Unit)?
}