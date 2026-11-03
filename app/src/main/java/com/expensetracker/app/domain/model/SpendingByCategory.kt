package com.expensetracker.app.domain.model

data class SpendingByCategory(
    val category: ExpenseCategory,
    val total: Double,
    val percentage: Float
)