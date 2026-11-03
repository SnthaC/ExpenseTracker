package com.expensetracker.app.data.repository

import com.expensetracker.app.data.local.BudgetDao
import com.expensetracker.app.domain.repository.BudgetRepository as BudgetRepositoryContract
import com.expensetracker.app.data.local.BudgetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) : BudgetRepositoryContract {

    override fun getBudget(): Flow<Double?> =
        budgetDao.getBudget().map { budget ->
            budget?.monthlyLimit
        }

    override suspend fun saveBudget(monthlyLimit: Double) {
        budgetDao.saveBudget(
            BudgetEntity(monthlyLimit = monthlyLimit)
        )
    }
}