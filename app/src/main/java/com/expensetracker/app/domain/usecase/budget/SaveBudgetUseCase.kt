package com.expensetracker.app.domain.usecase.budget

import com.expensetracker.app.domain.repository.BudgetRepository
import javax.inject.Inject

class SaveBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(monthlyLimit: Double) {
        require(monthlyLimit.isFinite() && monthlyLimit > 0.0) {
            "Budget must be greater than zero"
        }

        budgetRepository.saveBudget(monthlyLimit)
    }
}