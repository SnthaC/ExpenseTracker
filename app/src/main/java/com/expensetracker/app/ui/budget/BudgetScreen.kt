package com.expensetracker.app.ui.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensetracker.app.ui.budget.components.BudgetForm
import com.expensetracker.app.ui.budget.components.BudgetSavedContent
import com.expensetracker.app.ui.components.ErrorRetryContent
import com.expensetracker.app.ui.theme.AppColors

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.ScreenGradient)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.loadError != null -> {
                ErrorRetryContent(
                    message = uiState.loadError.orEmpty(),
                    onRetry = viewModel::retryLoading
                )
            }

            uiState.isSaved -> {
                BudgetSavedContent()
            }

            else -> {
                BudgetForm(
                    amount = uiState.amount,
                    isSaving = uiState.isSaving,
                    validationError = uiState.validationError,
                    saveError = uiState.saveError,
                    onAmountChange = viewModel::onAmountChange,
                    onSave = viewModel::saveBudget
                )
            }
        }
    }
}