package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Track
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * @author alexey@plainvanillagames.com
 * *         07/07/16.
 */
class TrackDataMapperTest {

    lateinit var track: Track
    lateinit var tracks: List<Track>

    lateinit var trackDataMapper: TrackDataMapper

    @Before
    fun setUp() {

        track = Track("track name", 10)
        tracks = listOf(track, track)

        trackDataMapper = TrackDataMapper()
    }

    @Test
    fun testTransformTrack() {
        // When
        var transformedTrack = trackDataMapper.transform(track)

        // Then
        assertEquals(track.name, transformedTrack.name)
        assertEquals(track.duration, transformedTrack.duration)
    }

    @Test
    fun testTransformTracks() {
        // When
        var transformedTracks = trackDataMapper.transform(tracks)

        // Then
        assertEquals(2, transformedTracks.size)
        assertEquals(track.name, transformedTracks[0].name)
        assertEquals(track.name, transformedTracks[1].name)
        assertEquals(track.duration, transformedTracks[0].duration)
        assertEquals(track.duration, transformedTracks[1].duration)
    }
}