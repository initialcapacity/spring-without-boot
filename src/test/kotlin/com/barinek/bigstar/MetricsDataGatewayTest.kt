package com.barinek.bigstar

import org.junit.Assert.assertEquals
import org.junit.Test

open class MetricsDataGatewayTest : TestDatabaseSupport() {
    @Test
    fun testCalculateMetrics() {
        jdbcTemplate().update("""
            insert into accounts (name, total_contract_value)
             values
             ('John\'s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val metrics = MetricsDataGateway(jdbcTemplate()).calculateMetrics()
        assertEquals(3, metrics.numberOfAccounts)
        assertEquals(7400000.00, metrics.totalContractValue, 0.0)
    }
}