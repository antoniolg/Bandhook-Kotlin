package com.antonioleiva.bandhookkotlin.repository.dataset

import com.antonioleiva.bandhookkotlin.domain.entity.Album

/**
 * @author tpom6oh@gmail.com
 *
 * 03/07/16.
 */
interface AlbumDataSet {

    fun requestTopAlbums(artistId: String?, artistName: String?): List<Album>
    fun requestAlbum(mbid: String): Album?

}