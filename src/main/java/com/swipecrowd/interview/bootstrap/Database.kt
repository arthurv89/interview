package com.swipecrowd.interview.bootstrap

import com.swipecrowd.interview.table.SettingsTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.ResultSet

object Database {
    val dbName = "interview"

    private fun createConfig(database: String): HikariConfig {
        return HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/$database"
            username = "postgres"
            password = "postgres"
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
        }
    }

    fun bootstrapPostgresDb() {
        HikariDataSource(createConfig("")).use { rootDataSource ->
            rootDataSource.connection.use { connection ->
                // Check if the database exists
                if (!databaseExists(connection, dbName)) {
                    println("Database '$dbName' does not exist. Creating it now...")
                    createDatabase(connection, dbName)
                    println("Database '$dbName' created successfully.")
                } else {
                    println("Database '$dbName' already exists.")
                }
            }
        }

        val hikariDataSource = HikariDataSource(createConfig(dbName))
        Database.connect(hikariDataSource)

        transaction {
            SchemaUtils.create(SettingsTable)
            SettingsTable.deleteAll()

            SettingsTable.insert {
                it[maxExpense] = 300
            }
        }
    }

    // Function to check if the database exists
    private fun databaseExists(connection: Connection, dbName: String): Boolean {
        val query = "SELECT 1 FROM pg_database WHERE datname = ?"
        connection.prepareStatement(query).use { stmt ->
            stmt.setString(1, dbName)
            val resultSet: ResultSet = stmt.executeQuery()
            return resultSet.next()
        }
    }

    // Function to create the database
    private fun createDatabase(connection: Connection, dbName: String) {
        val createDbQuery = "CREATE DATABASE $dbName"
        connection.createStatement().use { stmt ->
            stmt.executeUpdate(createDbQuery)
        }
    }
}