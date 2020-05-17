package collective.spring.metrics

import com.codahale.metrics.MetricRegistry
import com.fasterxml.jackson.databind.ObjectMapper
import io.collective.spring.rest.BasicApp
import io.collective.spring.rest.RestTemplate
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


open class MetricsControllerTest() {
    var app = TestApp(8081)

    @Before
    fun setUp() {
        app.start()
    }

    @After
    fun tearDown() {
        app.stop()
    }

    @Test
    fun testGetMetrics() {
        val port = 8081
        val response = RestTemplate().doGet("http://localhost:$port/api/metrics")
        assertTrue(response.contains("Meters"))
        assertTrue(response.contains("test-requests"))
    }

    ///

    class TestApp(port: Int) : BasicApp(port) {
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
                setConfigLocation("io.collective.spring.metrics,io.collective.spring.jdbc,collective.spring.metrics")
            }
        }
    }

    @Configuration
    @EnableWebMvc
    open class WebConfig : WebMvcConfigurer {
        override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
            converters.add(MappingJackson2HttpMessageConverter(ObjectMapper()))
        }

        @Bean
        open fun metricRegistry(): MetricRegistry {
            val registry = MetricRegistry()
            registry.meter("test-requests")
            return registry
        }
    }
}