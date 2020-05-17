package collective.spring

import io.collective.spring.App
import io.collective.spring.jdbc.DataSourceConfig
import io.collective.spring.rest.RestTemplate
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppTest() {
    var app = App(8081)

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
    fun testStart() {
        DataSourceConfig().getJdbcTemplate().update("""
            insert into accounts (name, total_contract_value)
             values
             ('John''s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val port = 8081

        val endpoint = "http://localhost:$port/"

        var response = RestTemplate().doGet(endpoint)
        assertTrue(response.contains("Spring without Boot"))

        response = RestTemplate().doGet("$endpoint/about.html")
        assertTrue(response.contains("unstyled about!"))

        response = RestTemplate().doGet("$endpoint/api/accounts")
        assertTrue(response.contains("John's Grocery, Inc."))
        assertTrue(response.contains("Hamburg Inn No. 2"))
        assertTrue(response.contains("Record Collector"))

        response = RestTemplate().doGet("$endpoint/api/metrics")
        assertTrue(response.contains("index-requests"))
        assertTrue(response.contains("count = 1"))
    }
}