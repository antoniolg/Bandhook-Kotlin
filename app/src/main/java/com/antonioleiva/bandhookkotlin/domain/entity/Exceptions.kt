package com.antonioleiva.bandhookkotlin.domain.entity

sealed class BizException {
    class AlbumNotFound(val id: String) : BizException()
    class ArtistNotFound(val id: String) : BizException()
}