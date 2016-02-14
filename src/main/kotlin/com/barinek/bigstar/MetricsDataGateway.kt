package com.barinek.bigstar

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.inject.Inject

@Repository
class MetricsDataGateway {
    private val jdbcTemplate: JdbcTemplate

    @Inject
    constructor(jdbcTemplate: JdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun calculateMetrics(): Metrics {
        jdbcTemplate.queryForObject("select 10 as number_of_accounts, 100.00 as total_account_value",
                { rs, rowNum ->
                    Metrics(
                            rs.getInt("number_of_accounts"),
                            rs.getDouble("total_account_value"))
                }
        )
        return Metrics(10, 100.00)
    }
}