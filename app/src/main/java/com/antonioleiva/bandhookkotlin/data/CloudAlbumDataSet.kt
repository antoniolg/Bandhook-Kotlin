package com.antonioleiva.bandhookkotlin.data

import com.antonioleiva.bandhookkotlin.data.lastfm.LastFmService
import com.antonioleiva.bandhookkotlin.data.mapper.AlbumMapper
import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.repository.dataset.AlbumDataSet

/**
 * @author alexey@plainvanillagames.com
 *
 * 03/07/16.
 */

class CloudAlbumDataSet(val lastFmService: LastFmService) : AlbumDataSet {

    override fun requestAlbum(id: String): Album? {
        val result = lastFmService.requestAlbum(id)
        return AlbumMapper().transform(result.album)
    }

    override fun requestTopAlbums(artistId: String?, artistName: String?): List<Album> {
        val mbid = artistId ?: ""
        val name = artistName ?: ""

        if (!mbid.isEmpty() || !name.isEmpty()) {
            val result = lastFmService.requestAlbums(mbid, name)
            return AlbumMapper().transform(result.topAlbums.albums)
        }

        return emptyList()
    }
}
