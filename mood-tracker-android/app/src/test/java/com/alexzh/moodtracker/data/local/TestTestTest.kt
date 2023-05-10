package com.alexzh.moodtracker.data.local

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class TestTestTest {

    @Test
    fun superImportantTest() {
        val a = Random.nextInt()
        val b = Random.nextInt()

        assertEquals(a + b, sum(a, b))
    }

    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}