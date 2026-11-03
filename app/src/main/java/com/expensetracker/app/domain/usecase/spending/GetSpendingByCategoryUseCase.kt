package com.expensetracker.app.domain.usecase.spending

import com.expensetracker.app.domain.model.ExpenseCategory
import javax.inject.Inject
import com.expensetracker.app.domain.model.Transaction
import com.expensetracker.app.domain.model.SpendingByCategory

class GetSpendingByCategoryUseCase @Inject constructor() {

    operator fun invoke(
        transactions: List<Transaction>
    ): List<SpendingByCategory> {

        val categorizedTransactions = transactions.mapNotNull { transaction ->
            val category = ExpenseCategory.entries.find {
                it.label == transaction.category
            }

            category?.let { it to transaction }
        }

        val totalSpending = categorizedTransactions.sumOf {
            it.second.amount
        }

        if (totalSpending <= 0.0) return emptyList()

        return categorizedTransactions
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
            .map { (category, categoryTransactions) ->
                val total = categoryTransactions.sumOf { it.amount }

                SpendingByCategory(
                    category = category,
                    total = total,
                    percentage = (total / totalSpending * 100).toFloat()
                )
            }
            .sortedByDescending { it.total }
    }
}