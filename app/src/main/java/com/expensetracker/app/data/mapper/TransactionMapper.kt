package com.expensetracker.app.data.mapper

import com.expensetracker.app.data.local.TransactionEntity
import com.expensetracker.app.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction =
    Transaction(
        id = id,
        title = title,
        amount = amount,
        category = category,
        date = date,
        note = note
    )

fun Transaction.toEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        category = category,
        date = date,
        note = note
    )