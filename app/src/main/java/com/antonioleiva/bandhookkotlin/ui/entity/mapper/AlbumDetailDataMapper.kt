package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.ui.entity.AlbumDetail

/**
 * @author alexey@plainvanillagames.com
 *
 * 05/07/16.
 */
class AlbumDetailDataMapper {

    fun transform(album: Album?): AlbumDetail? = if (album != null) AlbumDetail(
            album.id,
            album.name,
            album.url,
            album.tracks) else null
}