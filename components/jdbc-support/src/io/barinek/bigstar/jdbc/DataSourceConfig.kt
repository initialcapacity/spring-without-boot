package io.barinek.bigstar.jdbc

import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
open class DataSourceConfig {
    @Bean
    open fun getJdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(getDataSource())
    }

    fun getDataSource(): HikariDataSource {
        val json = System.getenv("VCAP_SERVICES")
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = from(json)
        return dataSource
    }

    private fun from(json: String): String? {
        val mapper = ObjectMapper()
        val root = mapper.readTree(json)
        val mysql = root.findValue("p-mysql")
        val credentials = mysql.findValue("credentials")
        return credentials.findValue("jdbcUrl").textValue()
    }
}