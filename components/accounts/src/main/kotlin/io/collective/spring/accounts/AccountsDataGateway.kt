package io.collective.spring.accounts

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class AccountsDataGateway constructor(private val jdbcTemplate: JdbcTemplate) {

    fun getAccounts(): List<Account> {
        return jdbcTemplate.query("select name, total_contract_value from accounts") { rs, _ ->
            Account(
                rs.getString("name"),
                rs.getDouble("total_contract_value"))
        }
    }
}