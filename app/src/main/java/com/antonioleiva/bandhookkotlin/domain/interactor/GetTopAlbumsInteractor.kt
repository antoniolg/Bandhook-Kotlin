package com.antonioleiva.bandhookkotlin.domain.interactor

import com.antonioleiva.bandhookkotlin.domain.interactor.base.Event
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Interactor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.TopAlbumsEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository

/**
 * @author tpom6oh@gmail.com
 *
 * 03/07/16.
 */

class GetTopAlbumsInteractor(val albumRepository: AlbumRepository) : Interactor {

    var artistId: String? = null
    var artistName: String? = null

    override fun invoke(): Event {
        if (artistId == null && artistName == null) {
            throw IllegalStateException("Either mbid or name should be specified")
        }
        val albums = albumRepository.getTopAlbums(artistId, artistName)
        return TopAlbumsEvent(albums)
    }
}