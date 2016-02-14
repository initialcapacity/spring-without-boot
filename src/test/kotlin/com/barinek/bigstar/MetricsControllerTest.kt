package com.barinek.bigstar

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Test

open class MetricsControllerTest : TestApp() {
    @Test
    fun testGetMetrics() {
        val response = doGet("http://localhost:8081/api/metrics")
        val metrics = ObjectMapper().readValue(response, Metrics::class.java)
        assertEquals(3, metrics.numberOfAccounts)
        assertEquals(7400000.00, metrics.totalContractValue, 0.0)
    }
}