package io.barinek.bigstar.accounts

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.inject.Inject

@Repository
class AccountsDataGateway {
    private val jdbcTemplate: JdbcTemplate

    @Inject
    constructor(jdbcTemplate: JdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun getAccounts(): List<Account> {
        return jdbcTemplate.query("select name, total_contract_value from accounts",
                { rs, rowNum ->
                    Account(
                            rs.getString("name"),
                            rs.getDouble("total_contract_value"))
                }
        )
    }
}