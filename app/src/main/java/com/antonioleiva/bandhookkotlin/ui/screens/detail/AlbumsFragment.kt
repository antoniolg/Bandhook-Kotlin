package com.antonioleiva.bandhookkotlin.ui.screens.detail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.adapter.ImageTitleAdapter
import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle

/**
 * @author alexey@plainvanillagames.com
 *
 * 01/07/16.
 */

class AlbumsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_albums, container, false)

        if (view != null) {
            setUpRecyclerView(view)
            return view
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById(R.id.albums_view) as RecyclerView
        val adapter = ImageTitleAdapter();
        mockAdapterContent(adapter)
        recyclerView.adapter = adapter;
    }

    private fun mockAdapterContent(adapter: ImageTitleAdapter) {
        val imageTile = ImageTitle("0", "The Dark Side of the Moon",
                "http://a.fastcompany.net/multisite_files/fastcompany/imagecache/inline-large/inline/2014/12/3039377-inline-i-3-the-dark-side-of-the-moon-cover-pf-dark-side-copy.jpg");
        adapter.items = listOf(imageTile, imageTile, imageTile, imageTile, imageTile, imageTile)
    }
}