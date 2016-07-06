package com.antonioleiva.bandhookkotlin.data.mapper

import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmArtist
import com.antonioleiva.bandhookkotlin.data.lastfm.model.LastFmTrack
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author alexey@plainvanillagames.com
 * *         06/07/16.
 */
class TrackMapperTest {

    lateinit var lastFmTracks: List<LastFmTrack>
    lateinit var lastFmTrack: LastFmTrack
    lateinit var lastFmArtist: LastFmArtist

    lateinit var trackMapper: TrackMapper

    @Before
    fun setUp() {
        lastFmArtist = LastFmArtist("artist name", "artist mbid", "artist url", emptyList(), null, null)
        lastFmTrack = LastFmTrack("track name", 10, null, "track url", lastFmArtist)
        lastFmTracks = listOf(lastFmTrack, lastFmTrack)

        trackMapper = TrackMapper()
    }

    @Test
    fun testTransformTracks() {
        // When
        val tracks = trackMapper.transform(lastFmTracks)

        // Then
        assertEquals(2, tracks.size)
        assertEquals(lastFmTracks[0].name, tracks[0].name)
        assertEquals(lastFmTracks[0].duration, tracks[0].duration)
        assertEquals(lastFmTracks[1].name, tracks[1].name)
        assertEquals(lastFmTracks[1].duration, tracks[1].duration)
    }

    @Test
    fun testTransformTracks_null() {
        // When
        val tracks = trackMapper.transform(null)

        // Then
        assertTrue(tracks.isEmpty())
    }

    @Test
    fun testTransformTracks_empty() {
        // When
        val tracks = trackMapper.transform(emptyList())

        // Then
        assertTrue(tracks.isEmpty())
    }

    @Test
    fun testTransformTrack() {
        // When
        val track = trackMapper.transform(lastFmTrack)

        // Then
        assertNotNull(track)
        assertEquals(lastFmTrack.name, track.name)
        assertEquals(lastFmTrack.duration, track.duration)
    }
}