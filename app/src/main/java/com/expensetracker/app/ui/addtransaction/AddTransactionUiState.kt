package com.expensetracker.app.ui.addtransaction

import com.expensetracker.app.domain.model.ExpenseCategory

data class AddTransactionUiState(
    val title: String = "",
    val amount: String = "",
    val selectedDate: Long = System.currentTimeMillis(),
    val category: String = ExpenseCategory.FOOD.label,

    val showDatePicker: Boolean = false,

    val titleError: Boolean = false,
    val amountError: Boolean = false,
    val categoryError: Boolean = false,

    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val transactionNotFound: Boolean = false,
    val loadError: String? = null,
    val saveError: String? = null
)