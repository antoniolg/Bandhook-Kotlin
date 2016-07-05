package com.antonioleiva.bandhookkotlin.ui.entity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * @author tpom6oh@gmail.com
 * *         04/07/16.
 */
class ImageTitleTest {

    @Test
    fun testInitWithEmptyUrl() {
        // When
        val imageTitle = ImageTitle("id", "name", "")

        // Then
        assertNull(imageTitle.url)
    }

    @Test
    fun testInitWithNullUrl() {
        // When
        val imageTitle = ImageTitle("id", "name", null)

        // Then
        assertNull(imageTitle.url)
    }

    @Test
    fun testInitWithNotEmptyUrl() {
        // When
        val imageTitle = ImageTitle("id", "name", "url")

        // Then
        assertEquals("url", imageTitle.url)
    }
}