package com.expensetracker.app.domain.usecase.transaction

import com.expensetracker.app.domain.model.Transaction
import com.expensetracker.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> =
        repository.getAllTransactions()
}