package com.expensetracker.app.domain.model

enum class ExpenseCategory(
    val label: String,
    val colorHex: Long
) {
    FOOD("Food", 0xFFF05B68),
    GROCERIES("Groceries", 0xFFF2A51A),
    TRANSPORT("Transport", 0xFF28B7C8),
    UTILITIES("Utilities", 0xFFF07845),
    SHOPPING("Shopping", 0xFF4CAF68),
    ENTERTAINMENT("Entertainment", 0xFF963EB0),
    SUBSCRIPTIONS("Subscriptions", 0xFFE83E78),
    HOUSING("Housing", 0xFFB34BD6),
    SPORT("Sport", 0xFF3F7BD9),
    HEALTH("Health", 0xFF26A69A),
    EDUCATION("Education", 0xFF5C6BC0),
    TRAVEL("Travel", 0xFF00897B),
    PERSONAL_CARE("Personal Care", 0xFFEC407A),
    GIFTS_DONATIONS("Gifts", 0xFF8D6E63),
    PETS("Pets", 0xFF9CCC65),
    CAFE("Café", 0xFF6D4C41),
    GAMES("Games", 0xFF7E57C2),
    BOOKS("Books", 0xFF3949AB),
    CLOTHES("Clothes", 0xFFAD1457),
    FAMILY("Family", 0xFF795548),
    RESTAURANTS("Restaurants", 0xFFE64A19),
    BARS_DRINKS("Bars & Drinks", 0xFF6D4C8D),
    OTHER("Other", 0xFF7777AA)
}