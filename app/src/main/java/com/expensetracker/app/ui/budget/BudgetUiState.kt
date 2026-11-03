package com.expensetracker.app.ui.budget

data class BudgetUiState(
    val amount: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val validationError: String? = null,
    val loadError: String? = null,
    val saveError: String? = null
)