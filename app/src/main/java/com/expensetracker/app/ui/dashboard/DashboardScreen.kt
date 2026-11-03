package com.expensetracker.app.ui.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensetracker.app.ui.components.ErrorRetryContent
import com.expensetracker.app.ui.dashboard.components.BudgetInfoCard
import com.expensetracker.app.ui.dashboard.components.DashboardPeriodDate
import com.expensetracker.app.ui.dashboard.components.DashboardPeriodSelector
import com.expensetracker.app.ui.dashboard.components.SpendingCategoryList
import com.expensetracker.app.ui.dashboard.components.SpendingDonutChart
import com.expensetracker.app.util.CurrencyFormatter

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val dashboardScrollState = rememberScrollState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            ErrorRetryContent(
                message = uiState.errorMessage.orEmpty(),
                onRetry = viewModel::retryLoading
            )
        }

        else -> {
            val totalSpending =
                uiState.spendingByCategory.sumOf { it.total }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (isLandscape) {
                            Modifier.verticalScroll(dashboardScrollState)
                        } else {
                            Modifier
                        }
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DashboardPeriodSelector(
                    selectedPeriod = uiState.selectedPeriod,
                    onPeriodSelected = viewModel::selectPeriod,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total spending",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = CurrencyFormatter.format(totalSpending),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (uiState.selectedPeriod == DashboardPeriod.BUDGET) {
                    BudgetInfoCard(
                        monthlyBudget = uiState.monthlyBudget,
                        totalSpending = totalSpending
                    )
                }

                SpendingDonutChart(
                    spendingByCategory = uiState.spendingByCategory,
                    centerText = if (
                        uiState.selectedPeriod == DashboardPeriod.TODAY
                    ) {
                        "for today"
                    } else {
                        "for this period"
                    },
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                DashboardPeriodDate(
                    selectedPeriod = uiState.selectedPeriod
                )

                SpendingCategoryList(
                    spendingByCategory = uiState.spendingByCategory,
                    scrollEnabled = !isLandscape
                )
            }
        }
    }
}