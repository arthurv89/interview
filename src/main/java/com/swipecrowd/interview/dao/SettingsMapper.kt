package com.swipecrowd.interview.dao

import com.swipecrowd.interview.model.Settings
import com.swipecrowd.interview.table.SettingsTable
import org.jetbrains.exposed.sql.ResultRow

object SettingsMapper {
    fun ResultRow.mapSettingsToModel(): Settings {
        return Settings(this[SettingsTable.maxExpense])
    }
}