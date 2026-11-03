package com.expensetracker.app.domain.usecase.transaction

import com.expensetracker.app.domain.repository.TransactionRepository
import com.expensetracker.app.domain.model.Transaction
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Int): Transaction? =
        repository.getTransactionById(id)
}