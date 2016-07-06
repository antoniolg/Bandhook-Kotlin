package com.antonioleiva.bandhookkotlin.ui.presenter

import com.antonioleiva.bandhookkotlin.ui.entity.ImageTitle

/**
 * @author tpom6oh@gmail.com
 *
 * 05/07/16.
 */

interface  AlbumsPresenter {
    fun onAlbumClicked(item: ImageTitle)
}