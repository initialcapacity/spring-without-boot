package com.barinek.bigstar

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class MetricsControllerTest : AppRunner() {
    @Test
    fun testGetMetrics() {
        val response = doGet("http://localhost:8080/api/metrics")
        val metrics = ObjectMapper().readValue(response, Metrics::class.java)
        assertEquals(10, metrics.numberOfAccounts)
        assertEquals(100.00, metrics.totalAccountValue, 0.0)
    }
}