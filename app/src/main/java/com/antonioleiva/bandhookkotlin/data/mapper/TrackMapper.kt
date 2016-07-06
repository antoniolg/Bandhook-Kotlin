package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmTrack
import com.antonioleiva.bandhookkotlin.domain.entity.Track

/**
 * @author alexey@plainvanillagames.com
 *
 * 05/07/16.
 */

class TrackMapper() {

    fun transform(tracks: List<LastFmTrack>?) : List<Track> {
        return tracks?.map { transform(it) } ?: emptyList()
    }

    fun transform(track: LastFmTrack) : Track = Track(track.name, track.duration)
}