package com.expensetracker.app.ui.addtransaction

sealed interface AddTransactionEvent {
    data object Saved : AddTransactionEvent
}