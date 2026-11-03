package com.expensetracker.app.ui.dashboard

import com.expensetracker.app.domain.model.SpendingByCategory

data class DashboardUiState(
    val selectedPeriod: DashboardPeriod = DashboardPeriod.TODAY,
    val spendingByCategory: List<SpendingByCategory> = emptyList(),
    val monthlyBudget: Double? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
