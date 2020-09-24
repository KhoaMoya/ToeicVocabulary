package com.khoa.toeicvocabulary.repository

import androidx.lifecycle.LiveData
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.local.AppSharedPreferences
import com.khoa.toeicvocabulary.repository.local.CategoryDao
import com.khoa.toeicvocabulary.repository.local.WordDao
import com.khoa.toeicvocabulary.utils.getPeriodDate
import com.khoa.toeicvocabulary.utils.getPeriodMonth
import com.khoa.toeicvocabulary.utils.getPeriodWeek

class AppRepository(
    val categoryDao: CategoryDao,
    val wordDao: WordDao,
    val preference: AppSharedPreferences
) {

    fun getAllWordList(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getAllWordsByCategory(it) }?: return wordDao.getAllWords()
    }

    fun getKnownWordList(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getLearnedWordListByCategory(it) }?: return wordDao.getLearnedWordList()
    }

    fun getUnKnownWordList(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getUnknownWordListByCategory(it) }?: return wordDao.getUnknownWordList()
    }

    fun getAllWordIds(categoryId: Int?):LiveData<List<Int>>{
        categoryId?.let { return wordDao.getAllWordIdsByCategory(it) }?: return wordDao.getAllWordIds()
    }

    fun getKnownWordIds(categoryId: Int?):LiveData<List<Int>>{
        categoryId?.let { return wordDao.getLearnedWordIdListByCategory(it) }?: return wordDao.getLearnedWordIds()
    }

    fun getUnKnownWordIds(categoryId: Int?):LiveData<List<Int>>{
        categoryId?.let { return wordDao.getUnknownWordIdListByCategory(it) }?: return wordDao.getUnknownWordIdList()
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