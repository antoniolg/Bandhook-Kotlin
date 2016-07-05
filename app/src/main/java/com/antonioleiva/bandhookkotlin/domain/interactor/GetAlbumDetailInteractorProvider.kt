package com.antonioleiva.bandhookkotlin.domain.interactor

import com.antonioleiva.bandhookkotlin.domain.interactor.base.Event
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Interactor
import com.antonioleiva.bandhookkotlin.domain.interactor.event.AlbumDetailEvent
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository

/**
 * @author tpom6oh@gmail.com
 *
 * 03/07/16.
 */

class GetAlbumDetailInteractor(val albumRepository: AlbumRepository) : Interactor {

    var albumId: String? = null

    override fun invoke(): Event {
        val id = albumId ?: throw IllegalStateException("Album id should be specified")

        val album = albumRepository.getAlbum(id)
        return AlbumDetailEvent(album)
    }

}