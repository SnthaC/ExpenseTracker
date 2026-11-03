package com.expensetracker.app.data.local

import android.content.Context
import androidx.room.Room

object ExpenseDatabaseProvider {

    @Volatile
    private var INSTANCE: ExpenseDatabase? = null

    fun getDatabase(context: Context): ExpenseDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java,
                "expense_database"
            ).build().also { INSTANCE = it }
        }
    }
}
