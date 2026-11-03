package com.expensetracker.app.ui.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.app.domain.model.Transaction
import com.expensetracker.app.domain.usecase.transaction.AddTransactionUseCase
import com.expensetracker.app.domain.usecase.transaction.GetTransactionByIdUseCase
import com.expensetracker.app.domain.usecase.transaction.UpdateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.expensetracker.app.domain.model.ExpenseCategory
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> =
        _uiState.asStateFlow()

    private val _events = Channel<AddTransactionEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private var originalTransaction: Transaction? = null
    private var loadedTransactionId: Int? = null

    fun onTitleChange(value: String) {
        _uiState.update {
            it.copy(title = value, titleError = false)
        }
    }

    fun onAmountChange(value: String) {
        _uiState.update {
            it.copy(amount = value, amountError = false)
        }
    }

    fun onDateSelected(value: Long) {
        _uiState.update {
            it.copy(
                selectedDate = value,
                showDatePicker = false
            )
        }
    }

    fun onShowDatePickerChange(show: Boolean) {
        _uiState.update {
            it.copy(showDatePicker = show)
        }
    }

    fun onCategorySelected(value: String) {
        _uiState.update {
            it.copy(category = value, categoryError = false)
        }
    }

    fun loadTransaction(transactionId: Int) {
        if (loadedTransactionId == transactionId) return
        loadedTransactionId = transactionId

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, loadError = null)
            }

            try {
                val transaction =
                    getTransactionByIdUseCase(transactionId)

                originalTransaction = transaction

                if (transaction == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            transactionNotFound = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            title = transaction.title,
                            amount = transaction.amount.toString(),
                            category = transaction.category,
                            selectedDate = transaction.date,
                            isLoading = false,
                            transactionNotFound = false
                        )
                    }
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loadError = "Couldn't load this transaction."
                    )
                }
            }
        }
    }

    fun saveTransaction() {
        val state = _uiState.value

        if (state.isLoading || state.isSaving) return

        val parsedAmount = state.amount.toDoubleOrNull()

        val titleError =
            state.title.isBlank() ||
                    state.title.none { it.isLetter() }

        val amountError =
            parsedAmount == null ||
                    !parsedAmount.isFinite() ||
                    parsedAmount <= 0

        val categoryError =
            ExpenseCategory.entries.none {
                it.label == state.category
            }

        _uiState.update {
            it.copy(
                titleError = titleError,
                amountError = amountError,
                categoryError = categoryError
            )
        }

        if (titleError || amountError || categoryError) return

        val transaction = Transaction(
            id = originalTransaction?.id ?: 0,
            title = state.title.trim(),
            amount = parsedAmount!!,
            category = state.category,
            date = state.selectedDate,
            note = originalTransaction?.note
        )

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSaving = true, saveError = null)
            }

            try {
                if (originalTransaction == null) {
                    addTransactionUseCase(transaction)
                } else {
                    updateTransactionUseCase(transaction)
                }

                _uiState.update {
                    it.copy(isSaving = false)
                }

                _events.send(AddTransactionEvent.Saved)

            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        saveError = "Couldn't save transaction. Please try again."
                    )
                }
            }
        }
    }
}