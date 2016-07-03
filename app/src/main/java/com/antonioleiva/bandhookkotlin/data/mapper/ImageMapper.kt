package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImage
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmImageType

/**
 * @author alexey@plainvanillagames.com
 *
 * 03/07/16.
 */

class ImageMapper {
    fun getMainImageUrl(images: List<LastFmImage>?): String? {
        val image = images?.firstOrNull { it.size == LastFmImageType.MEGA.type }
        return image?.url ?: images?.last()?.url
    }
}