package com.antonioleiva.bandhookkotlin.ui.screens.detail

import android.test.AndroidTestCase
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.mockito.Mockito.*

/**
 * @author alexey@plainvanillagames.com
 * *         03/07/16.
 */
class BiographyFragmentTest : AndroidTestCase() {

    lateinit var layoutInflater: LayoutInflater
    lateinit var mainView: View
    lateinit var biographyTextView: TextView
    lateinit var biographyFragment: BiographyFragment

    override fun setUp() {
        super.setUp()

        layoutInflater = mock(LayoutInflater::class.java)
        mainView = mock(View::class.java)
        biographyTextView = TextView(context)

        `when`(layoutInflater.inflate(anyInt(), any(ViewGroup::class.java), anyBoolean()))
                .thenReturn(mainView)
        `when`(mainView.findViewById(anyInt())).thenReturn(biographyTextView)

        biographyFragment = BiographyFragment()

        biographyFragment.onCreateView(layoutInflater, null, null)
    }

    fun testBiographyText() {
        // Given
        val htmlText = "<b>bold text</b>"

        // When
        biographyFragment.setBiographyText(htmlText)

        // Then
        assertEquals(Html.fromHtml(htmlText).toString(), biographyFragment.getBiographyText())
    }
}