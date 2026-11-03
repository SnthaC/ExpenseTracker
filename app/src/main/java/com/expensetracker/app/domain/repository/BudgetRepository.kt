package com.expensetracker.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getBudget(): Flow<Double?>
    suspend fun saveBudget(monthlyLimit: Double)
}