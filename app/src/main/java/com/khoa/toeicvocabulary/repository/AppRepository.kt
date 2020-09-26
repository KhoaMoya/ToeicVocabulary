package com.khoa.toeicvocabulary.repository

import androidx.lifecycle.LiveData
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.local.AppSharedPreferences
import com.khoa.toeicvocabulary.repository.local.CategoryDao
import com.khoa.toeicvocabulary.repository.local.WordDao
import com.khoa.toeicvocabulary.ui.home.DayWord
import com.khoa.toeicvocabulary.utils.getPeriodDate
import com.khoa.toeicvocabulary.utils.getPeriodDayRecentlyList
import com.khoa.toeicvocabulary.utils.getPeriodMonth
import com.khoa.toeicvocabulary.utils.getPeriodWeek

class AppRepository(
    val categoryDao: CategoryDao,
    val wordDao: WordDao,
    val preference: AppSharedPreferences
) {
    fun getDayWordRecentlyList(duration: Int = 7): ArrayList<DayWord>{
        val list = getPeriodDayRecentlyList(duration)
        list.forEach {
            it.count = wordDao.countLearnedWordListInTime(it.startTime, it.endTime)
        }
        return list
    }

    fun getAllWordList(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getAllWordsByCategory(it) }?: return wordDao.getAllWords()
    }

    fun getKnownWordList(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getLearnedWordListByCategory(it) }?: return wordDao.getLearnedWordList()
    }

    fun getUnKnownWordList(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getUnknownWordListByCategory(it) }?: return wordDao.getUnknownWordList()
    }

    fun countWordListLearnedToday(): LiveData<Int> {
        val times = getPeriodDate()
        return wordDao.countLearnedWordListInTime(times[0], times[1])
    }

    fun countWordListLearnedThisWeek(): LiveData<Int> {
        val times = getPeriodWeek()
        return wordDao.countLearnedWordListInTime(times[0], times[1])
    }

    fun countWordLearnedThisMonth(): LiveData<Int> {
        val times = getPeriodMonth()
        return wordDao.countLearnedWordListInTime(times[0], times[1])
    }

    fun getWordLearnedToday(): LiveData<List<Word>> {
        val times = getPeriodDate()
        return wordDao.getLearnedWordListInTime(times[0], times[1])
    }

    fun getWordLearnedThisWeek(): LiveData<List<Word>> {
        val times = getPeriodWeek()
        return wordDao.getLearnedWordListInTime(times[0], times[1])
    }

    fun getWordLearnedThisMonth(): LiveData<List<Word>> {
        val times = getPeriodMonth()
        return wordDao.getLearnedWordListInTime(times[0], times[1])
    }

}