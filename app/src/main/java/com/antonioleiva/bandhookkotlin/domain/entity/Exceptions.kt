package com.antonioleiva.bandhookkotlin.domain.entity

sealed class BizException {
    class AlbumNotFound(val id: String) : BizException()
    class ArtistNotFound(val id: String) : BizException()
    object RecomendationsNotFound : BizException()
    class TopAlbumsNotFound(val artistId: String?, val artistName: String?) : BizException()
}
