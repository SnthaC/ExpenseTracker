package com.expensetracker.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.expensetracker.app.domain.model.ExpenseCategory
import com.expensetracker.app.ui.theme.toColor

@Composable
fun CategoryIcon(
    category: ExpenseCategory,
    modifier: Modifier = Modifier
) {
    val icon = when (category) {
        ExpenseCategory.FOOD -> Icons.Default.Restaurant
        ExpenseCategory.GROCERIES -> Icons.Default.ShoppingCart
        ExpenseCategory.TRANSPORT -> Icons.Default.DirectionsCar
        ExpenseCategory.UTILITIES -> Icons.Default.Lightbulb
        ExpenseCategory.SHOPPING -> Icons.Default.ShoppingBag
        ExpenseCategory.ENTERTAINMENT -> Icons.Default.Movie
        ExpenseCategory.SUBSCRIPTIONS -> Icons.Default.Subscriptions
        ExpenseCategory.HOUSING -> Icons.Default.Home
        ExpenseCategory.SPORT -> Icons.Default.FitnessCenter
        ExpenseCategory.HEALTH -> Icons.Default.Favorite
        ExpenseCategory.EDUCATION -> Icons.Default.School
        ExpenseCategory.TRAVEL -> Icons.Default.Flight
        ExpenseCategory.PERSONAL_CARE -> Icons.Default.Spa
        ExpenseCategory.GIFTS_DONATIONS -> Icons.Default.CardGiftcard
        ExpenseCategory.PETS -> Icons.Default.Pets
        ExpenseCategory.CAFE -> Icons.Default.LocalCafe
        ExpenseCategory.GAMES -> Icons.Default.SportsEsports
        ExpenseCategory.BOOKS -> Icons.Default.MenuBook
        ExpenseCategory.CLOTHES -> Icons.Default.Checkroom
        ExpenseCategory.FAMILY -> Icons.Default.FamilyRestroom
        ExpenseCategory.RESTAURANTS -> Icons.Default.Restaurant
        ExpenseCategory.BARS_DRINKS -> Icons.Default.LocalBar
        ExpenseCategory.OTHER -> Icons.Default.MoreHoriz
    }

    Icon(
        imageVector = icon,
        contentDescription = category.label,
        tint = category.toColor(),
        modifier = modifier
    )
}