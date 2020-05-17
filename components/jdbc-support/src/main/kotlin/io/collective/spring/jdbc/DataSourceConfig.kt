package io.collective.spring.jdbc

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import java.net.URI

@Configuration
open class DataSourceConfig {
    @Bean
    open fun getJdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(getDataSource())
    }

    private fun getDataSource(): HikariDataSource {
        val databaseUrl = URI(System.getenv("DATABASE_URL"))

        return HikariDataSource().apply {
            jdbcUrl = from(databaseUrl)
        }
    }

    private fun from(databaseUrl: URI): String? {
        val userInfo = databaseUrl.userInfo.split(":").toTypedArray()
        val username = userInfo[0]
        val password = userInfo[1]
        return "jdbc:postgresql://${databaseUrl.host}:${databaseUrl.port}${databaseUrl.path}?user=$username&password=$password"
    }
}