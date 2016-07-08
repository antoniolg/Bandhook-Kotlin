package com.antonioleiva.bandhookkotlin.ui.screens.detail


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.bandhookkotlin.R
import com.antonioleiva.bandhookkotlin.ui.adapter.ImageTitleAdapter
import com.antonioleiva.bandhookkotlin.ui.fragment.AlbumsFragmentContainer

/**
 * @author tpom6oh@gmail.com
 *
 * 01/07/16.
 */

class AlbumsFragment: Fragment() {

    lateinit var adapter: ImageTitleAdapter
        private set
    lateinit var recycler: RecyclerView
        private set

    var albumsFragmentContainer: AlbumsFragmentContainer? = null
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_albums, container, false)

        if (view != null) {
            setUpRecyclerView(view)
            return view
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setUpRecyclerView(view: View) {
        recycler = view.findViewById(R.id.albums_view) as RecyclerView
        adapter = ImageTitleAdapter()
        recycler.adapter = adapter

        adapter.onItemClickListener = {
            albumsFragmentContainer?.getAlbumsPresenter()?.onAlbumClicked(it)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is AlbumsFragmentContainer) {
            albumsFragmentContainer = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        albumsFragmentContainer = null
    }
}