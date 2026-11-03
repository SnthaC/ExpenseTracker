package com.expensetracker.app.domain.usecase.budget

import com.expensetracker.app.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository
) {
    operator fun invoke(): Flow<Double?> =
        budgetRepository.getBudget()
}