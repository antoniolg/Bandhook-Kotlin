package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Track
import com.antonioleiva.bandhookkotlin.ui.entity.TrackDetail

/**
 * @author alexey@plainvanillagames.com
 *
 * 07/07/16.
 */

class TrackDataMapper {
    fun transform(domainTrack: Track): TrackDetail {
        return TrackDetail(domainTrack.name, domainTrack.duration)
    }

    fun transform(domainTrack: List<Track>): List<TrackDetail> {
        return domainTrack.map { transform(it) }
    }
}