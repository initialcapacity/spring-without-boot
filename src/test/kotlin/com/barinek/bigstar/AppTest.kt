package com.barinek.bigstar

import org.junit.Assert.assertTrue
import org.junit.Test

class AppTest : AppRunner() {
    @Test
    fun testStart() {
        val response = doGet("http://localhost:8080/index.html")
        assertTrue(response.contains("Hello World!"))
    }
}