package com.swipecrowd.interview.dao

import com.swipecrowd.interview.dao.SettingsMapper.mapSettingsToModel
import com.swipecrowd.interview.model.Settings
import com.swipecrowd.interview.table.SettingsTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component
class SettingsDao {
    fun getSettings(): Settings {
        return transaction {
            return@transaction SettingsTable.selectAll()
                .first()
                .mapSettingsToModel()
        }
    }
}
