package barinek.bigstar

import com.fasterxml.jackson.databind.ObjectMapper
import io.barinek.bigstar.App
import io.barinek.bigstar.jdbc.DataSourceConfig
import io.barinek.bigstar.metrics.Metrics
import io.barinek.bigstar.rest.RestTemplate
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


open class MetricsControllerTest() {
    var app: AppTest.TestApp = AppTest.TestApp()

    @Before
    fun setUp() {
        DataSourceConfig().getJdbcTemplate().execute("delete from accounts")
        app.start()
    }

    @After
    fun tearDown() {
        app.stop()
    }

    @Test
    fun testGetMetrics() {
        DataSourceConfig().getJdbcTemplate().update("""
            insert into accounts (name, total_contract_value)
             values
             ('John\'s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val port = 8081
        val response = RestTemplate().doGet("http://localhost:$port/api/metrics")
        val metrics = ObjectMapper().readValue(response, Metrics::class.java)
        assertEquals(3, metrics.numberOfAccounts)
        assertEquals(7400000.00, metrics.totalContractValue, 0.0)
    }

    class TestApp : App() {
        override fun getPort(): Int {
            return 8081
        }
    }
}