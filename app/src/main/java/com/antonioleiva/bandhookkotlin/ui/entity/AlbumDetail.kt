package com.antonioleiva.bandhookkotlin.ui.entity

import com.antonioleiva.bandhookkotlin.domain.entity.Track

/**
 * @author alexey@plainvanillagames.com
 *
 * 05/07/16.
 */
data class AlbumDetail(val id: String,
                       val name: String,
                       val url: String?,
                       val tracks: List<Track>)