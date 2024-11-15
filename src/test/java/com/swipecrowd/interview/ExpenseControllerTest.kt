package com.swipecrowd.interview

import com.fasterxml.jackson.databind.ObjectMapper
import com.swipecrowd.interview.application.ExpensesApplication
import com.swipecrowd.interview.model.Expense
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.util.UUID

@ExtendWith(BeforeAllTests::class)
@SpringBootTest(classes = [ExpensesApplication::class], webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ExpenseControllerTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @LocalServerPort private val port: Int,
    @Autowired val mockMvc: MockMvc
) {
    val testCalls = TestCalls("http://localhost:$port", restTemplate)

    @Test
    fun testPostAndGet() {
        val notFoundResponse: ResponseEntity<Expense>? = testCalls.doGet("/${UUID.randomUUID()}")
        assertThat(notFoundResponse?.body).isNull()

        val tooHighExpense = createExpense(value = 5151)

        val response = mockMvc.perform(
            post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(tooHighExpense))
        ).andReturn().response
        assertThat(response.status).isEqualTo(400)

        val expense = createExpense(value = 100)
        val createdExpenseResponse: ResponseEntity<Expense>? = testCalls.doPost("/", expense)
        val createdExpense = createdExpenseResponse?.body
        assertThat(createdExpense).isNotNull

        val createdUuid = createdExpense!!.id
        val getResponse: ResponseEntity<Expense>? = testCalls.doGet("/$createdUuid")
        assertThat(getResponse?.body).isEqualTo(createdExpense)
    }

    private fun createExpense(value: Int) = Expense(null, value, "descr")
}
