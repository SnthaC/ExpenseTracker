package com.expensetracker.app.ui.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.app.domain.usecase.budget.GetBudgetUseCase
import com.expensetracker.app.domain.usecase.budget.SaveBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException


@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val saveBudgetUseCase: SaveBudgetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> =
        _uiState.asStateFlow()

    private var loadBudgetJob: Job? = null

    init {
        loadBudget()
    }

    private fun loadBudget() {
        loadBudgetJob?.cancel()

        loadBudgetJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    loadError = null
                )
            }

            getBudgetUseCase()
                .catch { exception ->
                    if (exception is CancellationException) throw exception

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loadError = "Unable to load budget."
                        )
                    }
                }
                .collect { budget ->
                    _uiState.update { current ->
                        current.copy(
                            amount = if (
                                current.amount.isEmpty() && budget != null
                            ) {
                                "%.2f".format(
                                    java.util.Locale.UK,
                                    budget
                                )
                            } else {
                                current.amount
                            },
                            isLoading = false,
                            loadError = null
                        )
                    }
                }
        }
    }

    fun retryLoading() {
        if (_uiState.value.isLoading) return
        if (_uiState.value.loadError == null) return

        loadBudget()
    }

    fun onAmountChange(value: String) {
        _uiState.update {
            it.copy(
                amount = value,
                isSaved = false,
                validationError = null,
                saveError = null
            )
        }
    }

    fun saveBudget() {
        val currentState = _uiState.value

        if (currentState.isLoading || currentState.isSaving) return
        if (currentState.loadError != null) return

        val amount = currentState.amount
            .replace(",", "")
            .toDoubleOrNull()

        if (amount == null || !amount.isFinite() || amount <= 0.0) {
            _uiState.update {
                it.copy(
                    validationError = "Enter an amount greater than zero"
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    isSaved = false,
                    validationError = null,
                    saveError = null
                )
            }

            try {
                saveBudgetUseCase(amount)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isSaved = true,
                        saveError = null
                    )
                }

            } catch (exception: CancellationException) {
                throw exception

            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        saveError = "Unable to save budget."
                    )
                }
            }
        }
    }
}