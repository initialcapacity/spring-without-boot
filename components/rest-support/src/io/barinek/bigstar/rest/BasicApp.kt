package io.barinek.bigstar.rest

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.slf4j.LoggerFactory

abstract class BasicApp
constructor() {
    private val logger = LoggerFactory.getLogger(BasicApp::class.java)
    private val server: Server

    init {
        val list = handlerList()

        server = Server(getPort())
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

    protected abstract fun getPort(): Int

    protected abstract fun handlerList(): HandlerList

    @Throws(Exception::class)
    fun start() {
        logger.info("App started.")
        server.start()
    }

    @Throws(Exception::class)
    fun stop() {
        logger.info("App stopped.")
        server.stop()
    }
}