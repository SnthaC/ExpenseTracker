package com.expensetracker.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.expensetracker.app.ui.addtransaction.AddTransactionScreen
import com.expensetracker.app.ui.budget.BudgetScreen
import com.expensetracker.app.ui.dashboard.DashboardScreen
import com.expensetracker.app.ui.transaction.TransactionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.DASHBOARD,
        modifier = modifier
    ) {
        composable(AppRoutes.DASHBOARD) {
            DashboardScreen()
        }

        composable(AppRoutes.TRANSACTIONS) {
            TransactionScreen(
                onEditTransaction = { transactionId ->
                    navController.navigate(
                        AppRoutes.addTransaction(transactionId)
                    )
                }
            )
        }

        composable(AppRoutes.BUDGET) {
            BudgetScreen()
        }

        composable(
            route = AppRoutes.ADD_TRANSACTION,
            arguments = listOf(
                navArgument("transactionId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->

            val transactionId = backStackEntry.arguments
                ?.getInt("transactionId")
                ?.takeIf { it != -1 }

            AddTransactionScreen(
                transactionId = transactionId,
                onSaved = {
                    navController.navigate(AppRoutes.TRANSACTIONS) {
                        popUpTo(AppRoutes.ADD_TRANSACTION) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}