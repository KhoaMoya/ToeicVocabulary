package com.khoa.toeicvocabulary.utils

import com.khoa.toeicvocabulary.ui.home.DayWord
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

fun getPeriodDayRecentlyList(duration: Int): ArrayList<DayWord>{
    val list = ArrayList<DayWord>()
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    calendar.add(Calendar.DAY_OF_MONTH, 0-duration)
    val mock = calendar.timeInMillis
    val milesDay  = 86400000L
    val df = SimpleDateFormat("dd/MM", Locale.getDefault())

    for(i in 0 .. duration){
        val dayWord = DayWord()
        dayWord.startTime = mock + i * milesDay
        dayWord.endTime = dayWord.startTime + milesDay
        calendar.timeInMillis = dayWord.startTime
        dayWord.dateName = df.format(calendar.time)
        list.add(dayWord)
    }
    return list
}
