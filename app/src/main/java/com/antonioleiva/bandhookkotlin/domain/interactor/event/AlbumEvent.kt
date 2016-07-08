package com.antonioleiva.bandhookkotlin.domain.interactor.event

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.interactor.base.Event

/**
 * @author tpom6oh@gmail.com
 *
 * 03/07/16.
 */

data class AlbumEvent(val album: Album?) : Event