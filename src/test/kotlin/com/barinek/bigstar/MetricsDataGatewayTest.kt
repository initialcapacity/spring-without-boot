package com.barinek.bigstar

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class MetricsDataGatewayTest {
    @Test
    fun testCalculateMetrics() {
        val properties = Properties()
        properties.load(javaClass.getResourceAsStream("/test.properties"))

        val config = DataSourceConfig()
        config.containerJson = properties.getProperty("container.json")
        val metrics = MetricsDataGateway(config.getJdbcTemplate()).calculateMetrics()
        assertEquals(10, metrics.numberOfAccounts)
        assertEquals(100.00, metrics.totalAccountValue, 0.0)
    }
}