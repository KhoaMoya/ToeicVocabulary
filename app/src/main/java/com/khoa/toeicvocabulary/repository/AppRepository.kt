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

    fun getAllWords(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getAllWordsByCategory(it) }?: return wordDao.getAllWords()
    }

    fun getKnownWords(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getLearnedWordsByCategory(it) }?: return wordDao.getLearnedWords()
    }

    fun getUnKnownWords(categoryId: Int?):LiveData<List<Word>>{
        categoryId?.let { return wordDao.getUnknownWordsByCategory(it) }?: return wordDao.getUnknownWords()
    }

    fun countWordsLearnedToday(): LiveData<Int> {
        val times = getPeriodDate()
        return wordDao.countLearnedWordsInTime(times[0], times[1])
    }

    fun countWordsLearnedThisWeek(): LiveData<Int> {
        val times = getPeriodWeek()
        return wordDao.countLearnedWordsInTime(times[0], times[1])
    }

    fun countWordsLearnedThisMonth(): LiveData<Int> {
        val times = getPeriodMonth()
        return wordDao.countLearnedWordsInTime(times[0], times[1])
    }

    fun getWordsLearnedToday(): LiveData<List<Word>> {
        val times = getPeriodDate()
        return wordDao.getLearnedWordsInTime(times[0], times[1])
    }

    fun getWordsLearnedThisWeek(): LiveData<List<Word>> {
        val times = getPeriodWeek()
        return wordDao.getLearnedWordsInTime(times[0], times[1])
    }

    fun getWordsLearnedThisMonth(): LiveData<List<Word>> {
        val times = getPeriodMonth()
        return wordDao.getLearnedWordsInTime(times[0], times[1])
    }

}