/*
 * Copyright (C) 2016 Alexey Verein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.antonioleiva.bandhookkotlin.ui.entity.mapper

import com.antonioleiva.bandhookkotlin.domain.entity.Track
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

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
        val transformedTrack = trackDataMapper.transform(1, track)

        // Then
        assertEquals(1, transformedTrack.number)
        assertEquals(track.name, transformedTrack.name)
        assertEquals(track.duration, transformedTrack.duration)
    }

    @Test
    fun testTransformTracks() {
        // When
        val transformedTracks = trackDataMapper.transform(tracks)

        // Then
        assertEquals(2, transformedTracks.size)
        assertEquals(track.name, transformedTracks[0].name)
        assertEquals(track.name, transformedTracks[1].name)
        assertEquals(track.duration, transformedTracks[0].duration)
        assertEquals(track.duration, transformedTracks[1].duration)
    }
}