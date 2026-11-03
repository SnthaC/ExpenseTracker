package com.expensetracker.app.ui.transaction.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.expensetracker.app.domain.model.Transaction
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private data class TransactionDateGroup(
    val date: LocalDate,
    val transactions: List<Transaction>
)

@Composable
fun TransactionList(
    transactions: List<Transaction>,
    onEditTransaction: (Int) -> Unit,
    onDeleteTransaction: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    val groupedTransactions = remember(transactions) {
        transactions
            .groupBy { transaction ->
                Instant.ofEpochMilli(transaction.date)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
            .toSortedMap(compareByDescending { it })
            .map { (date, dailyTransactions) ->
                TransactionDateGroup(
                    date = date,
                    transactions = dailyTransactions.sortedByDescending { it.id }
                )
            }
    }

    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.UK)
    }

    LazyColumn(
        modifier = modifier
    ) {
        item(key = "transactions_title") {
            Text(
                text = "Transactions",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        item(key = "transactions_spacing") {
            Spacer(modifier = Modifier.height(16.dp))
        }

        groupedTransactions.forEach { group ->

            item(key = "date_${group.date}") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 4.dp)
                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                        thickness = 1.dp
                    )

                    Text(
                        text = group.date.format(dateFormatter),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 10.dp,
                            bottom = 8.dp
                        )
                    )
                }
            }

            items(
                items = group.transactions,
                key = { transaction -> transaction.id }
            ) { transaction ->

                TransactionListItem(
                    transaction = transaction,
                    onEditClick = {
                        onEditTransaction(transaction.id)
                    },
                    onDeleteClick = {
                        onDeleteTransaction(transaction)
                    }
                )
            }
        }
    }
}