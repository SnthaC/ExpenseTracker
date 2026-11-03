package com.expensetracker.app.ui.transaction

sealed interface TransactionEvent {
    data object DeleteSucceeded : TransactionEvent

    data class ShowSnackbar(
        val message: String
    ) : TransactionEvent
}