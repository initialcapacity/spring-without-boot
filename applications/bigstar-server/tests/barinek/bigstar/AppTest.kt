package barinek.bigstar

import io.barinek.bigstar.App
import io.barinek.bigstar.rest.RestTemplate
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppTest() {
    var app: TestApp = TestApp()

    @Before
    fun setUp() {
        app.start()
    }

    @After
    fun tearDown() {
        app.stop()
    }

    @Test
    fun testStart() {
        val port = 8081
        var response = RestTemplate().doGet("http://localhost:$port/index.html")
        assertTrue(response.contains("Hello World!"))

        response = RestTemplate().doGet("http://localhost:$port/about/index.html")
        assertTrue(response.contains("About!"))
    }

    class TestApp : App() {
        override fun getPort(): Int {
            return 8081
        }
    }
}