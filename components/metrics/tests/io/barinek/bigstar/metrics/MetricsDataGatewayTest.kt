package io.barinek.bigstar.metrics

import io.barinek.bigstar.jdbc.DataSourceConfig
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

open class MetricsDataGatewayTest {
    @Before
    fun setUp() {
        DataSourceConfig().getJdbcTemplate().execute("delete from accounts")
    }

    @Test
    fun testCalculateMetrics() {
        DataSourceConfig().getJdbcTemplate().update("""
            insert into accounts (name, total_contract_value)
             values
             ('John\'s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val metrics = MetricsDataGateway(DataSourceConfig().getJdbcTemplate()).calculateMetrics()
        assertEquals(3, metrics.numberOfAccounts)
        assertEquals(7400000.00, metrics.totalContractValue, 0.0)
    }
}