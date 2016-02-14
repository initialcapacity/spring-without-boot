package com.barinek.bigstar

import org.junit.Assert.assertTrue
import org.junit.Test

class AppTest : TestApp() {
    @Test
    fun testStart() {
        val response = doGet("http://localhost:8081/index.html")
        assertTrue(response.contains("Hello World!"))
    }
}