package com.expensetracker.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.app.domain.usecase.budget.GetBudgetUseCase
import com.expensetracker.app.domain.usecase.spending.GetMonthlySpendingUseCase
import com.expensetracker.app.domain.usecase.spending.GetTodaySpendingUseCase
import com.expensetracker.app.domain.usecase.transaction.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.coroutines.cancellation.CancellationException


@HiltViewModel
class DashboardViewModel @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase,
    private val getTodaySpendingUseCase: GetTodaySpendingUseCase,
    private val getMonthlySpendingUseCase: GetMonthlySpendingUseCase,
    private val getBudgetUseCase: GetBudgetUseCase
) : ViewModel() {

    private val _selectedPeriod =
        MutableStateFlow(DashboardPeriod.TODAY)

    private val selectedPeriod = _selectedPeriod.asStateFlow()

    private val retryTrigger = MutableStateFlow(0)

    fun selectPeriod(period: DashboardPeriod) {
        _selectedPeriod.value = period
    }

    val uiState = retryTrigger
        .flatMapLatest {
            combine(
                getTransactionsUseCase(),
                getBudgetUseCase(),
                selectedPeriod
            ) { transactions, monthlyBudget, period ->

                val spendingByCategory = when (period) {
                    DashboardPeriod.TODAY ->
                        getTodaySpendingUseCase(transactions)

                    DashboardPeriod.THIS_MONTH,
                    DashboardPeriod.BUDGET ->
                        getMonthlySpendingUseCase(transactions)
                }

                DashboardUiState(
                    selectedPeriod = period,
                    spendingByCategory = spendingByCategory,
                    monthlyBudget = monthlyBudget,
                    isLoading = false
                )

            }
                .onStart {
                    emit(
                        DashboardUiState(
                            selectedPeriod = selectedPeriod.value,
                            isLoading = true
                        )
                    )
                }
                .catch { exception ->

                    if (exception is CancellationException) throw exception

                    emit(
                        DashboardUiState(
                            selectedPeriod = selectedPeriod.value,
                            isLoading = false,
                            errorMessage = "Couldn't load dashboard data."
                        )
                    )
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState()
        )

    fun retryLoading() {
        if (uiState.value.isLoading) return

        retryTrigger.update { it + 1 }
    }
}