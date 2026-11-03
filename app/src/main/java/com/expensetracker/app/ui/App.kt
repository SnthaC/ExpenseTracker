package com.expensetracker.app.ui

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.expensetracker.app.ui.navigation.AppRoutes
import com.expensetracker.app.ui.navigation.AppTab
import com.expensetracker.app.ui.navigation.NavGraph
import com.expensetracker.app.ui.navigation.components.AppBottomBar

@Composable
fun App() {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectedTab = when (currentRoute) {
        AppRoutes.TRANSACTIONS -> AppTab.TRANSACTIONS
        AppRoutes.BUDGET -> AppTab.BUDGET
        else -> AppTab.HOME
    }

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButtonPosition = FabPosition.Center,

        bottomBar = {
            AppBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->

                    val route = when (tab) {
                        AppTab.HOME -> AppRoutes.DASHBOARD
                        AppTab.TRANSACTIONS -> AppRoutes.TRANSACTIONS
                        AppTab.BUDGET -> AppRoutes.BUDGET
                        AppTab.MORE -> null
                    }

                    route?.let {
                        navController.navigate(it) {
                            popUpTo(AppRoutes.DASHBOARD) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                }
            )
        },

        floatingActionButton = {
            if (
                currentRoute == AppRoutes.DASHBOARD ||
                currentRoute == AppRoutes.TRANSACTIONS ||
                currentRoute == AppRoutes.BUDGET
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            AppRoutes.addTransaction(-1)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.offset(y = 45.dp),
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add expense"
                    )
                }
            }
        }
    ) { innerPadding ->

        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}