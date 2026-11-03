package com.expensetracker.app.domain.usecase.spending

import com.expensetracker.app.domain.model.SpendingByCategory
import com.expensetracker.app.domain.model.Transaction
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetTodaySpendingUseCase @Inject constructor(
    private val getSpendingByCategoryUseCase: GetSpendingByCategoryUseCase
) {
    operator fun invoke(
        transactions: List<Transaction>
    ): List<SpendingByCategory> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)

        val startOfDay = today.atStartOfDay(zone).toInstant().toEpochMilli()
        val startOfTomorrow = today.plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val todayTransactions = transactions.filter {
            it.date >= startOfDay && it.date < startOfTomorrow
        }

        return getSpendingByCategoryUseCase(todayTransactions)
    }
}