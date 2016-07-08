package com.antonioleiva.bandhookkotlin.ui.view

import com.antonioleiva.bandhookkotlin.ui.entity.AlbumDetail

/**
 * @author tpom6oh@gmail.com
 *
 * 05/07/16.
 */
interface AlbumView: PresentationView {
    fun showAlbum(albumDetail: AlbumDetail?)
}