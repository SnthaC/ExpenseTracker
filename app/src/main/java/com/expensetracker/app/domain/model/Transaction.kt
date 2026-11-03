package com.expensetracker.app.domain.model

data class Transaction(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val date: Long,
    val note: String? = null
)