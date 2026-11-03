package com.expensetracker.app.domain.usecase.spending

import com.expensetracker.app.domain.model.Transaction
import javax.inject.Inject
import com.expensetracker.app.domain.model.SpendingByCategory
import java.time.LocalDate
import java.time.ZoneId

class GetMonthlySpendingUseCase @Inject constructor(
    private val getSpendingByCategoryUseCase: GetSpendingByCategoryUseCase
) {
    operator fun invoke(
        transactions: List<Transaction>
    ): List<SpendingByCategory> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)

        val startOfMonth = today
            .withDayOfMonth(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val startOfNextMonth = today
            .withDayOfMonth(1)
            .plusMonths(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val monthlyTransactions = transactions.filter {
            it.date >= startOfMonth && it.date < startOfNextMonth
        }
        return getSpendingByCategoryUseCase(monthlyTransactions)
    }
}