package com.antonioleiva.bandhookkotlin.domain.interactor.event

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Event

/**
 * @author alexey@plainvanillagames.com
 *
 * 03/07/16.
 */

data class AlbumDetailEvent(val album: Album?) : Event