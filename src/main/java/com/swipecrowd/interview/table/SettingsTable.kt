package com.swipecrowd.interview.table

import org.jetbrains.exposed.sql.Table

object SettingsTable : Table("settings") {
    val id = integer("id").autoIncrement()
    val maxExpense = integer("max_expense")
    override val primaryKey = PrimaryKey(id)
}