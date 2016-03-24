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

package com.antonioleiva.bandhookkotlin.ui.util

import android.animation.ObjectAnimator
import android.os.Build
import android.support.v4.view.ViewGroupCompat
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.antonioleiva.bandhookkotlin.ui.adapter.SingleClickListener

fun View.animateEnter() = animateTranslationY(0, DecelerateInterpolator(3f))
fun View.animateExit() = animateTranslationY(-height, AccelerateInterpolator(3f))

fun View.animateTranslationY(translationY: Int, interpolator: Interpolator) {
    with(ObjectAnimator.ofFloat(this, "translationY", translationY.toFloat()))
    {
        setDuration(context.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
        setInterpolator(interpolator)
        start()
    }
}

/**
 * Click listener setter that prevents double click on the view it´s set
 */
fun View.singleClick(l: (android.view.View?) -> Unit){
    setOnClickListener(SingleClickListener(l))
}

fun View.setTransitionGroupCompat(isTransitionGroup: Boolean) {
    if (this is ViewGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setTransitionGroup(isTransitionGroup)
        else
            ViewGroupCompat.setTransitionGroup(this, isTransitionGroup)
    }
}