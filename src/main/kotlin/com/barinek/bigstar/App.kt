package com.barinek.bigstar

import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.slf4j.LoggerFactory
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import java.io.IOException
import java.sql.SQLException
import java.util.*

class App @Throws(IOException::class, ClassNotFoundException::class, SQLException::class)

constructor() {
    private val server: Server

    init {
        val properties = Properties()
        properties.load(javaClass.getResourceAsStream("/default.properties"))

        val list = HandlerList()
        list.addHandler(getServletContextHandler(getContext()))

        server = Server(tryPort(Integer.parseInt(properties.getProperty("server.port"))))
        server.handler = list
        server.stopAtShutdown = true;

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                try {
                    if (server.isRunning) {
                        server.stop()
                    }
                    logger.info("App shutdown.")
                } catch (e: Exception) {
                    logger.info("Error shutting down app.", e)
                }
            }
        })
    }

    private fun getServletContextHandler(context: WebApplicationContext): Handler? {
        val contextHandler = ServletContextHandler()
        contextHandler.contextPath = "/"
        contextHandler.addServlet(ServletHolder(DispatcherServlet(context)), "/*")
        contextHandler.addEventListener(ContextLoaderListener(context))
        return contextHandler
    }

    private fun getContext(): WebApplicationContext {
        val context = AnnotationConfigWebApplicationContext()
        context.setConfigLocation("com.barinek.bigstar")
        return context
    }

    @Throws(Exception::class)
    fun start() {
        logger.info("App started.")
        server.start()
    }

    private fun tryPort(defaultPort: Int): Int {
        return if (System.getenv("PORT") != null) Integer.parseInt(System.getenv("PORT")) else defaultPort
    }

    companion object {
        private val logger = LoggerFactory.getLogger(App::class.java)

        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            App().start()
        }
    }
}