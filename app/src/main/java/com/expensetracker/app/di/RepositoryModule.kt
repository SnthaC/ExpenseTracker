package com.expensetracker.app.di

import com.expensetracker.app.data.repository.BudgetRepository
import com.expensetracker.app.domain.repository.BudgetRepository as BudgetRepositoryContract
import com.expensetracker.app.data.repository.TransactionRepository
import com.expensetracker.app.domain.repository.TransactionRepository as TransactionRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        implementation: TransactionRepository
    ): TransactionRepositoryContract

    @Binds
    @Singleton
    abstract fun bindBudgetRepository(
        implementation: BudgetRepository
    ): BudgetRepositoryContract
}