package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonioleiva.bandhookkotlin.R

/**
 * @author alexey@plainvanillagames.com
 *
 * 02/07/16.
 */

class BiographyFragment: Fragment() {

    var biographyTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_biography, container, false)

        if (view != null) {
            biographyTextView = view.findViewById(R.id.biography) as TextView
            return view
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun setBiographyText(biographyText: String?) {
        biographyTextView?.text = Html.fromHtml(biographyText)
    }
}