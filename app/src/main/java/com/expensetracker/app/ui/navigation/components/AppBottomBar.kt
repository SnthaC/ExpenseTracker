package com.expensetracker.app.ui.navigation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.expensetracker.app.ui.navigation.AppTab

@Composable
fun AppBottomBar(
    selectedTab: AppTab,
    onTabSelected: (AppTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = BottomBarCutoutShape(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarItem(
                label = AppTab.HOME.label,
                icon = Icons.Default.Home,
                selected = selectedTab == AppTab.HOME,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(AppTab.HOME) }
            )

            BottomBarItem(
                label = AppTab.TRANSACTIONS.label,
                icon = Icons.Default.ReceiptLong,
                selected = selectedTab == AppTab.TRANSACTIONS,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(AppTab.TRANSACTIONS) }
            )

            Spacer(Modifier.width(56.dp))

            BottomBarItem(
                label = AppTab.BUDGET.label,
                icon = Icons.Default.AccountBalanceWallet,
                selected = selectedTab == AppTab.BUDGET,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(AppTab.BUDGET) }
            )

            BottomBarItem(
                label = AppTab.MORE.label,
                icon = Icons.Default.MoreHoriz,
                selected = selectedTab == AppTab.MORE,
                modifier = Modifier.weight(1f),
                onClick = { onTabSelected(AppTab.MORE) }
            )
        }
    }
}