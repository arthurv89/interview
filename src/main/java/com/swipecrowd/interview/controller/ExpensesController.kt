package com.swipecrowd.interview.controller

import com.swipecrowd.interview.model.Expense
import com.swipecrowd.interview.service.ExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ExpensesController(
    @Autowired val expenseService: ExpenseService
) {
//    @GetMapping("/")
//    fun listExpenses() = expenseService.createOrUpdateExpenses()

    @GetMapping("/{id}")
    fun getExpense(@PathVariable id: UUID) = expenseService.getExpense(id)

    @PostMapping("/")
    @PutMapping("/")
    fun createExpense(@RequestBody expense: Expense) = expenseService.createOrUpdateExpenses(expense)

//    @DeleteMapping("/")
//    fun deleteExpense() = expenseService.deleteExpenses()
}
