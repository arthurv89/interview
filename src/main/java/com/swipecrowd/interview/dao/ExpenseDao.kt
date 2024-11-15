package com.swipecrowd.interview.dao

import com.swipecrowd.interview.bootstrap.DynamoDbClientProvider
import com.swipecrowd.interview.dao.ExpenseMapper.expenseItemToModel
import com.swipecrowd.interview.dao.ExpenseMapper.expenseModelToItem
import com.swipecrowd.interview.model.Expense
import com.swipecrowd.interview.model.ExpenseItem
import com.swipecrowd.interview.model.ExpenseItemSchema
import org.springframework.stereotype.Component
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException
import java.util.UUID

@Component
class ExpenseDao {
    val table: DynamoDbTable<ExpenseItem> = DynamoDbClientProvider.current.table(ExpenseItemSchema.tableName, ExpenseItemSchema.schema)

//    fun listAll(): List<Expense> {
//        return listOf()
//    }

    fun getExpenses(id: UUID): Expense? {
        return try {
            table.getItem(
                Key.builder().partitionValue(id.toString()).build()
            )?.expenseItemToModel()
        } catch (e: ResourceNotFoundException) {
            return null
        }
    }

    fun createOrUpdateExpenses(expense: Expense): Expense {
        val item = expense.expenseModelToItem()
        table.putItemWithResponse { builder ->
            builder.item(item)
        }
        return item.expenseItemToModel()
    }

//    fun update(): Expense {
//    }
//
//    fun delete(): Unit {
//
//    }
}
