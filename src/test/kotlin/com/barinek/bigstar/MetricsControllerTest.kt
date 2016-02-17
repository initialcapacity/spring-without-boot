package com.barinek.bigstar

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Test

open class MetricsControllerTest : TestApp() {
    @Test
    fun testGetMetrics() {
        jdbcTemplate().update("""
            insert into accounts (name, total_contract_value)
             values
             ('John\'s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val port = Integer.parseInt(System.getenv("PORT"))
        val response = doGet("http://localhost:$port/api/metrics")
        val metrics = ObjectMapper().readValue(response, Metrics::class.java)
        assertEquals(3, metrics.numberOfAccounts)
        assertEquals(7400000.00, metrics.totalContractValue, 0.0)
    }
}