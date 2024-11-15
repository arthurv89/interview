package com.swipecrowd.interview.dao

import com.swipecrowd.interview.model.Expense
import com.swipecrowd.interview.model.ExpenseItem
import java.util.UUID

object ExpenseMapper {
    fun ExpenseItem.expenseItemToModel(): Expense {
        return Expense(
            id = UUID.fromString(this.id),
            value = this.value!!,
            description = this.description!!,
            date = this.date!!,
        )
    }

    fun Expense.expenseModelToItem(): ExpenseItem {
        return ExpenseItem(
            id = id?.toString() ?: UUID.randomUUID().toString(),
            value = value,
            description = description,
            date = date,
        )
    }
}