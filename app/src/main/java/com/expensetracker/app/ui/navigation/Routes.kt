package com.expensetracker.app.ui.navigation

object AppRoutes {
    const val DASHBOARD = "dashboard"
    const val TRANSACTIONS = "transactions"
    const val BUDGET = "budget"
    const val ADD_TRANSACTION = "add_transaction/{transactionId}"

    fun addTransaction(transactionId: Int) =
        "add_transaction/$transactionId"
  }