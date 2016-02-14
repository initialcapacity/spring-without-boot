package com.barinek.bigstar

import org.junit.Assert.assertEquals
import org.junit.Test

open class MetricsDataGatewayTest : TestDatabaseSupport() {
    @Test
    fun testCalculateMetrics() {
        val metrics = MetricsDataGateway(jdbcTemplate()).calculateMetrics()
        assertEquals(3, metrics.numberOfAccounts)
        assertEquals(7400000.00, metrics.totalContractValue, 0.0)
    }
}