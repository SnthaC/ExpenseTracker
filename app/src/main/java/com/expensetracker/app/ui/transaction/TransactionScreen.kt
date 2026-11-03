package com.expensetracker.app.ui.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensetracker.app.ui.components.ConfirmDialog
import com.expensetracker.app.ui.components.ErrorRetryContent
import com.expensetracker.app.ui.transaction.components.TransactionList

@Composable
fun TransactionScreen(
    onEditTransaction: (Int) -> Unit,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var transactionToDeleteId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    val transactionToDelete = uiState.transactions.find {
        it.id == transactionToDeleteId
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                TransactionEvent.DeleteSucceeded -> {
                    transactionToDeleteId = null
                }

                is TransactionEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    ErrorRetryContent(
                        message = uiState.errorMessage.orEmpty(),
                        onRetry = viewModel::retryLoading
                    )
                }

                uiState.transactions.isEmpty() -> {
                    Text(
                        text = "No transactions yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                else -> {
                    TransactionList(
                        transactions = uiState.transactions,
                        onEditTransaction = onEditTransaction,
                        onDeleteTransaction = { transaction ->
                            transactionToDeleteId = transaction.id
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    transactionToDelete?.let { transaction ->
        ConfirmDialog(
            title = "Delete transaction?",
            message = "Are you sure you want to delete \"${transaction.title}\"?",
            confirmText = "Delete",
            onConfirm = {
                viewModel.deleteTransaction(transaction)
            },
            onDismiss = {
                transactionToDeleteId = null
            }
        )
    }
}