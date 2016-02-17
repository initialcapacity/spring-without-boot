package com.barinek.bigstar

import org.flywaydb.core.Flyway
import org.junit.After
import org.junit.Before

open class TestDatabaseSupport {
    val config = DataSourceConfig()
    val flyway = Flyway()

    init {
        flyway.dataSource = config.getDataSource()
    }

    @Before
    fun setup() {
        flyway.clean()
        flyway.migrate()
    }

    fun jdbcTemplate() = config.getJdbcTemplate()

    @After
    fun tearDown() {
        flyway.clean()
    }
}

