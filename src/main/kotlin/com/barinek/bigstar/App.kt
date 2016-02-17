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

open class App @Throws(IOException::class, ClassNotFoundException::class, SQLException::class)

constructor() {
    private val logger = LoggerFactory.getLogger(App::class.java)
    private val server: Server

    init {
        val list = HandlerList()
        list.addHandler(getServletContextHandler(getContext()))

        server = Server(Integer.parseInt(System.getenv("PORT")))
        server.handler = list
        server.stopAtShutdown = true;

        Runtime.getRuntime().addShutdownHook(Thread({
            try {
                if (server.isRunning) {
                    server.stop()
                }
                logger.info("App shutdown.")
            } catch (e: Exception) {
                logger.info("Error shutting down app.", e)
            }
        }))
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
            setConfigLocation("com.barinek.bigstar")
        }
    }

    @Throws(Exception::class)
    fun start() {
        logger.info("App started.")
        server.start()
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            App().start()
        }
    }
}