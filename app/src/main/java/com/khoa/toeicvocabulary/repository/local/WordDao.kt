package com.khoa.toeicvocabulary.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.khoa.toeicvocabulary.models.Word

@Dao
interface WordDao {

    @Query("select count(*) from word")
    fun countAllWords(): LiveData<Int>

    @Query("select * from word")
    fun getAllWords(): LiveData<List<Word>>

    @Query("select * from word where category_id=:categoryId")
    fun getAllWordsByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select count(*) from word where status=1")
    fun countLearnedWords(): LiveData<Int>

    @Query("select * from word where status=1")
    fun getLearnedWords(): LiveData<List<Word>>

    @Query("select * from word where status=0")
    fun getUnknownWords(): LiveData<List<Word>>

    @Query("select * from word where status=1 and category_id=:categoryId")
    fun getLearnedWordsByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select * from word where status=0 and category_id=:categoryId")
    fun getUnknownWordsByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select count(*) from word where status=1 and category_id=:categoryId")
    fun countLearnedWords(categoryId: Int): Int

    @Query("select count(*) from word where status=0 and category_id=:categoryId")
    fun countUnknownWords(categoryId: Int): Int

    @Query("select count(*) from word where status=1 and date>=:startTime and date<:endTime")
    fun countLearnedWordsInTime(startTime: Long, endTime: Long): LiveData<Int>

    @Query("select * from word where status=1 and date>=:startTime and date<:endTime")
    fun getLearnedWordsInTime(startTime: Long, endTime: Long): LiveData<List<Word>>

    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Delete
    fun delete(word: Word)
}