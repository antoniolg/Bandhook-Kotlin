package com.antonioleiva.bandhookkotlin.repository

import com.antonioleiva.bandhookkotlin.domain.entity.Album
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.repository.dataset.AlbumDataSet

/**
 * @author alexey@plainvanillagames.com
 *
 * 03/07/16.
 */

class AlbumRepositoryImp(val albumDataSets: List<AlbumDataSet>) : AlbumRepository {

    override fun getAlbum(id: String): Album? {
        for (dataSet in albumDataSets) {
            var result = dataSet.requestAlbum(id)
            if (result != null) {
                return result
            }
        }

        return null
    }

    override fun getTopAlbums(artistId: String?, artistName: String?): List<Album> {
        for (dataSet in albumDataSets) {
            var result = dataSet.requestTopAlbums(artistId, artistName)
            if (result.isNotEmpty()) {
                return result
            }
        }

        return emptyList()
    }

}