package com.expensetracker.app.ui.transaction

import com.expensetracker.app.domain.model.Transaction

data class TransactionUiState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)