package com.barinek.bigstar

import org.flywaydb.core.Flyway
import org.junit.After
import org.junit.Before
import java.util.*

open class TestDatabaseSupport {
    val properties = Properties()
    val config = DataSourceConfig()
    val flyway = Flyway()

    init {
        properties.load(javaClass.getResourceAsStream("/test.properties"))
        config.containerJson = properties.getProperty("container.json")
        flyway.dataSource = config.getDataSource()
    }

    @Before
    fun setup() {
        flyway.clean()
        flyway.setLocations("db/migration", "db/fixture")
        flyway.migrate()
    }

    fun jdbcTemplate() = config.getJdbcTemplate()

    @After
    fun tearDown() {
        flyway.clean()
    }
}

