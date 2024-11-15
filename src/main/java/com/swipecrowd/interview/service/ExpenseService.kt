package com.swipecrowd.interview.service

import com.swipecrowd.interview.dao.ExpenseDao
import com.swipecrowd.interview.dao.SettingsDao
import com.swipecrowd.interview.model.Expense
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ExpenseService(
    @Autowired val expenseDao: ExpenseDao,
    @Autowired val settingsDao: SettingsDao,
) {
    fun getExpense(id: UUID) = expenseDao.getExpenses(id)

    fun createOrUpdateExpenses(expense: Expense): Expense {
        val settings = settingsDao.getSettings()
        return if(expense.value <= settings.maxExpense) {
            return expenseDao.createOrUpdateExpenses(expense)
        } else {
            throw ExpenseTooHighException()
        }
    }
}
