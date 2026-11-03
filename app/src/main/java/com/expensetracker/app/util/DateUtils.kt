package com.expensetracker.app.util

fun localDateToPickerMillis(millis: Long): Long {
    val zone = java.time.ZoneId.systemDefault()
    val date = java.time.Instant.ofEpochMilli(millis)
        .atZone(zone)
        .toLocalDate()

    return date.atStartOfDay(java.time.ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
}

fun pickerMillisToLocalTimestamp(
    pickerMillis: Long
): Long {
    val date = java.time.Instant.ofEpochMilli(pickerMillis)
        .atZone(java.time.ZoneOffset.UTC)
        .toLocalDate()

    return date.atStartOfDay(java.time.ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}