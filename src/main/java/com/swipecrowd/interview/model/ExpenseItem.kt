package com.swipecrowd.interview.model

import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags
import java.time.LocalDateTime

data class ExpenseItem(
    var id: String?,
    var value: Int?,
    var description: String?,
    var date: LocalDateTime?,
)

object ExpenseItemSchema {
    val tableName = "expenses"

    val schema: TableSchema<ExpenseItem> =
        TableSchema
            .builder(ExpenseItem::class.java)
            .newItemSupplier(::createEmptyItem)
            .addAttribute(String::class.java) {
                it
                    .name("id")
                    .getter(ExpenseItem::id)
                    .setter(ExpenseItem::id::set)
                    .tags(StaticAttributeTags.primaryPartitionKey())
            }
            .addAttribute(Int::class.java) {
                it
                    .name("value")
                    .getter(ExpenseItem::value)
                    .setter(ExpenseItem::value::set)
            }
            .addAttribute(String::class.java) {
                it
                    .name("description")
                    .getter(ExpenseItem::description)
                    .setter(ExpenseItem::description::set)
            }
            .addAttribute(LocalDateTime::class.java) {
                it
                    .name("date")
                    .getter(ExpenseItem::date)
                    .setter(ExpenseItem::date::set)
            }
            .build()

    private fun createEmptyItem(): ExpenseItem {
        return ExpenseItem(
            id = null,
            value = null,
            description = null,
            date = null,
        )
    }
}