package com.expensetracker.app.ui.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.Composable
import com.expensetracker.app.ui.addtransaction.components.AddTransactionContent

@Composable
fun AddTransactionScreen(
    transactionId: Int? = null,
    viewModel: AddTransactionViewModel = hiltViewModel(),
    onSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(transactionId) {
        transactionId?.let(viewModel::loadTransaction)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                AddTransactionEvent.Saved -> onSaved()
            }
        }
    }

    AddTransactionContent(
        uiState = uiState,
        isEditing = transactionId != null,
        onTitleChange = viewModel::onTitleChange,
        onAmountChange = viewModel::onAmountChange,
        onDateSelected = viewModel::onDateSelected,
        onShowDatePickerChange = viewModel::onShowDatePickerChange,
        onCategorySelected = viewModel::onCategorySelected,
        onSave = viewModel::saveTransaction
    )
}