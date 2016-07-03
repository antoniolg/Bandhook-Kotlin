package com.antonioleiva.bandhookkotlin.domain.repository

import com.antonioleiva.bandhookkotlin.domain.entity.Album

/**
 * @author alexey@plainvanillagames.com
 *
 * 03/07/16.
 */
interface AlbumRepository {
    fun getTopAlbums(artistId: String?, artistName: String?): List<Album>
    fun getAlbum(id: String): Album?
}