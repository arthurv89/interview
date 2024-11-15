package com.swipecrowd.interview

import com.swipecrowd.interview.application.ExpensesApplication
import com.swipecrowd.interview.model.Expense
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import java.time.LocalDateTime
import java.util.UUID

@ExtendWith(BeforeAllTests::class)
@SpringBootTest(classes = [ExpensesApplication::class], webEnvironment = WebEnvironment.RANDOM_PORT)
class ExpenseControllerTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @LocalServerPort private val port: Int
) {
    val testCalls = TestCalls("http://localhost:$port", restTemplate)

    @Test
    fun testPostAndGet() {
        val response: Expense? = testCalls.doGet("/${UUID.randomUUID()}")
        assertThat(response).isNull()

        val body = Expense(null, 5151, "descr", LocalDateTime.now())
        val createExpense: Expense? = testCalls.doPost("/", body)
        assertThat(createExpense).isNotNull

        val createdUuid = createExpense!!.id
        val response2: Expense? = testCalls.doGet("/$createdUuid")
        assertThat(response2).isEqualTo(createExpense)
    }
}
