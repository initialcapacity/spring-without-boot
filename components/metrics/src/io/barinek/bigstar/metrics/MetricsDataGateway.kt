package io.barinek.bigstar.metrics

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
        return jdbcTemplate.queryForObject("select count(1) as number_of_accounts, sum(total_contract_value) as total_contract_value from accounts",
                { rs, rowNum ->
                    Metrics(
                            rs.getInt("number_of_accounts"),
                            rs.getDouble("total_contract_value"))
                }
        )
    }
}