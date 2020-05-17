package collective.spring.accounts

import io.collective.spring.accounts.AccountsDataGateway
import io.collective.spring.jdbc.DataSourceConfig
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

open class AccountsDataGatewayTest {
    @Before
    fun setUp() {
        DataSourceConfig().getJdbcTemplate().execute("delete from accounts")
    }

    @Test
    fun testGetAccounts() {
        DataSourceConfig().getJdbcTemplate().update("""
            insert into accounts (name, total_contract_value)
             values
             ('John''s Grocery, Inc.', 6000000),
             ('Hamburg Inn No. 2', 0),
             ('Record Collector', 1400000)
        """)

        val accounts = AccountsDataGateway(DataSourceConfig().getJdbcTemplate()).getAccounts()
        assertEquals(3, accounts.size)
    }
}