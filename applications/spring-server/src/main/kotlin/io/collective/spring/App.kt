package io.collective.spring

import io.collective.spring.rest.BasicApp
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import java.util.*

class App(port: Int) : BasicApp(port) {
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
            setConfigLocation("io.collective.spring")
        }
    }
}

fun main() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val port = System.getenv("PORT")?.toInt() ?: 8888
    App(port).start()
}