package com.swipecrowd.interview.model

import java.util.UUID

data class Expense(
    val id: UUID?,
    val value: Int,
    val description: String,
)
