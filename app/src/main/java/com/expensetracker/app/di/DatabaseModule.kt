package com.expensetracker.app.di

import android.content.Context
import androidx.room.Room
import com.expensetracker.app.data.local.BudgetDao
import com.expensetracker.app.data.local.ExpenseDatabase
import com.expensetracker.app.data.local.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase =
        Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_database"
        ).build()

    @Provides
    fun provideTransactionDao(
        database: ExpenseDatabase
    ): TransactionDao = database.transactionDao()

    @Provides
    fun provideBudgetDao(
        database: ExpenseDatabase
    ): BudgetDao = database.budgetDao()
}