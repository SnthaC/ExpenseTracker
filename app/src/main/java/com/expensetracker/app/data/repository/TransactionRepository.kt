package com.expensetracker.app.data.repository

import com.expensetracker.app.data.local.TransactionDao
import com.expensetracker.app.domain.repository.TransactionRepository as TransactionRepositoryContract
import com.expensetracker.app.data.mapper.toDomain
import com.expensetracker.app.data.mapper.toEntity
import com.expensetracker.app.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepositoryContract {

    override fun getAllTransactions(): Flow<List<Transaction>> =
    transactionDao.getAllTransactions().map { transactions ->
        transactions.map { it.toDomain() }
    }

    override suspend fun getTransactionById(id: Int): Transaction? {
        return transactionDao.getTransactionById(id)?.toDomain()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }
}
