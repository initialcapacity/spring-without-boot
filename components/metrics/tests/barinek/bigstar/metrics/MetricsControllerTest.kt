package barinek.bigstar.metrics

import com.fasterxml.jackson.databind.ObjectMapper
import io.barinek.bigstar.jdbc.DataSourceConfig
import io.barinek.bigstar.metrics.Metrics
import io.barinek.bigstar.rest.BasicApp
import io.barinek.bigstar.rest.RestTemplate
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


open class MetricsControllerTest() {
    var app: TestApp = TestApp()

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

    ///

    class TestApp : BasicApp() {
        override fun getPort(): Int {
            return 8081
        }

        override fun handlerList(): HandlerList {
            val list = HandlerList()
            list.addHandler(getServletContextHandler(getContext()))
            return list
        }

        private fun getServletContextHandler(context: WebApplicationContext): Handler {
            return ServletContextHandler().apply {
                contextPath = "/"
                addServlet(ServletHolder(DispatcherServlet(context)), "/*")
                addEventListener(ContextLoaderListener(context))
            }
        }

        private fun getContext(): WebApplicationContext {
            return AnnotationConfigWebApplicationContext().apply {
                setConfigLocation("io.barinek.bigstar.metrics,io.barinek.bigstar.jdbc,barinek.bigstar.metrics")
            }
        }
    }

    @Configuration
    @EnableWebMvc
    open class WebConfig : WebMvcConfigurerAdapter() {
        override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
            converters.add(MappingJackson2HttpMessageConverter(ObjectMapper()))
        }
    }
}