package com.khoa.toeicvocabulary.utils

import java.util.*

fun getPeriodDate(date: Date): Array<Long> {
    val result = arrayOf(0L, 0L)
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.time = date
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    result[0] = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    result[1] = calendar.timeInMillis

    return result
}

fun getPeriodDate(): Array<Long> {
    val result = arrayOf(0L, 0L)
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    result[0] = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    result[1] = calendar.timeInMillis

    return result
}

fun getPeriodWeek(): Array<Long> {
    val result = arrayOf(0L, 0L)
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    result[0] = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, 7)
    result[1] = calendar.timeInMillis
    return result
}

fun getPeriodMonth(): Array<Long> {
    val result = arrayOf(0L, 0L)
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar[Calendar.DAY_OF_MONTH] = 1
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    result[0] = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    result[1] = calendar.timeInMillis
    return result
}
