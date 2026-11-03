package com.expensetracker.app.ui.addtransaction.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.expensetracker.app.ui.addtransaction.AddTransactionUiState
import com.expensetracker.app.ui.components.AppButton
import com.expensetracker.app.ui.theme.AppColors
import com.expensetracker.app.ui.theme.AppInputStyle
import com.expensetracker.app.ui.theme.AppShapes

@Composable
fun AddTransactionContent(
    uiState: AddTransactionUiState,
    isEditing: Boolean,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onDateSelected: (Long) -> Unit,
    onShowDatePickerChange: (Boolean) -> Unit,
    onCategorySelected: (String) -> Unit,
    onSave: () -> Unit
) {
    val isLandscape =
        LocalConfiguration.current.orientation ==
                Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.ScreenGradient)
            .padding(16.dp)
            .then(
                if (isLandscape) {
                    Modifier.verticalScroll(rememberScrollState())
                } else {
                    Modifier
                }
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.transactionNotFound -> {
                Text("Transaction not found.")
            }

            uiState.loadError != null -> {
                Text(
                    text = uiState.loadError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            else -> {
                Text(
                    text = if (isEditing) {
                        "Edit transaction"
                    } else {
                        "Add transaction"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.titleError,
                    supportingText = {
                        if (uiState.titleError) {
                            Text(
                                if (uiState.title.isBlank()) {
                                    "Enter a title"
                                } else {
                                    "Title must contain at least one letter"
                                }
                            )
                        }
                    },
                    shape = AppShapes.input,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppInputStyle.background,
                        unfocusedContainerColor = AppInputStyle.background,
                        focusedBorderColor = AppInputStyle.border,
                        unfocusedBorderColor = AppInputStyle.border
                    )
                )

                TransactionDateSelector(
                    selectedDate = uiState.selectedDate,
                    onDateSelected = onDateSelected,
                    showDatePicker = uiState.showDatePicker,
                    onShowDatePickerChange = onShowDatePickerChange
                )

                AmountInput(
                    amount = uiState.amount,
                    onAmountChange = onAmountChange,
                    amountError = uiState.amountError
                )

                Text(
                    text = "Category",
                    style = MaterialTheme.typography.titleMedium
                )

                CategorySelector(
                    selectedCategory = uiState.category,
                    isLandscape = isLandscape,
                    onCategorySelected = onCategorySelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (isLandscape) {
                                Modifier
                            } else {
                                Modifier.weight(1f)
                            }
                        )
                )

                if (uiState.categoryError) {
                    Text(
                        text = "Select a category",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                uiState.saveError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                AppButton(
                    text = when {
                        uiState.isSaving -> "Saving..."
                        isEditing -> "Save changes"
                        else -> "Save transaction"
                    },
                    onClick = onSave,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}