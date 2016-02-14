package com.barinek.bigstar

import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
open class DataSourceConfig {
    @Value("\${container.json}")
    private val containerJson: String = ""

    @Bean
    open fun getJdbcTemplate(): JdbcTemplate {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = from(containerJson)
        return JdbcTemplate(dataSource)
    }

    fun from(json: String): String? {
        val mapper = ObjectMapper()
        val root = mapper.readTree(json)
        val mysql = root.findValue("p-mysql")
        val credentials = mysql.findValue("credentials")
        return credentials.findValue("jdbcUrl").textValue()
    }
}