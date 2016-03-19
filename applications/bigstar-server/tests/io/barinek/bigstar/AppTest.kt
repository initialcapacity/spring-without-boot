package io.barinek.bigstar

import org.junit.Assert.assertTrue
import org.junit.Test

class AppTest : TestApp() {
    @Test
    fun testStart() {
        val port = Integer.parseInt(System.getenv("PORT"))
        val response = doGet("http://localhost:$port/index.html")
        assertTrue(response.contains("Hello World!"))
    }
}