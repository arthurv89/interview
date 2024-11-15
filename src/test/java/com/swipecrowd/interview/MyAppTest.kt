package com.swipecrowd.interview

import com.swipecrowd.interview.TestContainerUtils.createTestAwsSettings
import com.swipecrowd.interview.bootstrap.Bootstrap
import com.swipecrowd.interview.bootstrap.DynamoDbClientProvider
import com.swipecrowd.interview.model.ExpenseItem
import com.swipecrowd.interview.model.ExpenseItemSchema
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import software.amazon.awssdk.core.retry.backoff.FixedDelayBackoffStrategy
import software.amazon.awssdk.core.waiters.WaiterOverrideConfiguration
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import java.time.Duration

class BeforeAllTests : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        Bootstrap.bootstrap(createTestAwsSettings())

        createTable(ExpenseItemSchema.tableName, ExpenseItemSchema.schema)
    }

    private fun createTable(tableName: String, schema: TableSchema<ExpenseItem>) {
        DynamoDbClientProvider.current
            .table(tableName, schema)
            .createTable()
        DynamoDbClientProvider.lowLevelClient.waiter().waitUntilTableExists(
            createDescribeTableRequest(tableName),
            waiterOverrideConfig
        )
    }

    private val waiterOverrideConfig = WaiterOverrideConfiguration.builder()
        .backoffStrategy(FixedDelayBackoffStrategy.create(Duration.ofMillis(20L)))
        .maxAttempts(200)
        .build()

    private fun createDescribeTableRequest(tableName: String) = DescribeTableRequest.builder()
        .tableName(tableName)
        .build()
}
