package com.expensetracker.app.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.app.domain.model.Transaction
import com.expensetracker.app.domain.usecase.transaction.DeleteTransactionUseCase
import com.expensetracker.app.domain.usecase.transaction.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> =
        _uiState.asStateFlow()

    private val _events = Channel<TransactionEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase()
                .catch { exception ->
                    if (exception is CancellationException) throw exception

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Couldn't load transactions."
                        )
                    }
                }
                .collect { transactions ->
                    _uiState.update {
                        it.copy(
                            transactions = transactions,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun retryLoading() {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        observeTransactions()
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                deleteTransactionUseCase(transaction)
                _events.send(TransactionEvent.DeleteSucceeded)

            } catch (exception: CancellationException) {
                throw exception

            } catch (exception: Exception) {
                _events.send(
                    TransactionEvent.ShowSnackbar(
                        "Couldn't delete transaction. Please try again."
                    )
                )
            }
        }
    }
}