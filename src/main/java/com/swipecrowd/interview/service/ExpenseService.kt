package com.swipecrowd.interview.service

import com.swipecrowd.interview.dao.ExpenseDao
import com.swipecrowd.interview.model.Expense
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ExpenseService(
    @Autowired val expenseDao: ExpenseDao
) {
    fun getExpense(id: UUID) = expenseDao.getExpenses(id)
//    fun listExpenses() = expenseDao.listAll()
    fun createOrUpdateExpenses(expense: Expense) = expenseDao.createOrUpdateExpenses(expense)
//    fun updateExpenses() = expenseDao.update()
//    fun deleteExpenses() = expenseDao.delete()
}
