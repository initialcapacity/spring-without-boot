package collective.spring.accounts

import com.fasterxml.jackson.databind.ObjectMapper
import io.collective.spring.jdbc.DataSourceConfig
import io.collective.spring.rest.BasicApp
import io.collective.spring.rest.getForString
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
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


open class AccountsControllerTest() {
    var app = TestApp(8081)

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
             ('John''s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val port = 8081
        val response = RestTemplate().getForString("http://localhost:$port/api/accounts")
        val accounts = ObjectMapper().readValue(response, List::class.java)
        assertEquals(3, accounts.size)
    }

    ///

    class TestApp(post: Int) : BasicApp(post) {
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
                setConfigLocation("io.collective.spring.accounts,io.collective.spring.jdbc,collective.spring.accounts")
            }
        }
    }

    @Configuration
    @EnableWebMvc
    open class WebConfig : WebMvcConfigurer {
        override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
            converters.add(MappingJackson2HttpMessageConverter(ObjectMapper()))
        }
    }
}