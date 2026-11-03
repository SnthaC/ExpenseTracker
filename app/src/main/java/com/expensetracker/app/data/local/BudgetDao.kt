package com.expensetracker.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budget WHERE id = 1")
    fun getBudget(): Flow<BudgetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBudget(budget: BudgetEntity)
}
