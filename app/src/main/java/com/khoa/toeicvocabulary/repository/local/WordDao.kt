package com.khoa.toeicvocabulary.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.khoa.toeicvocabulary.models.Word

@Dao
interface WordDao {

    // word --------------
    @Query("select * from word where id=:id")
    fun getWord(id: Int): Word

    // all word -----------
    @Query("select id from word")
    fun getAllWordIds(): LiveData<List<Int>>

    @Query("select count(*) from word")
    fun countAllWords(): LiveData<Int>

    @Query("select * from word")
    fun getAllWords(): LiveData<List<Word>>

    @Query("select * from word where category_id=:categoryId")
    fun getAllWordsByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select id from word where category_id=:categoryId")
    fun getAllWordIdsByCategory(categoryId: Int): LiveData<List<Int>>

    // known word ----------
    @Query("select id from word where status=1")
    fun getLearnedWordIds(): LiveData<List<Int>>

    @Query("select count(*) from word where status=1")
    fun countLearnedWord(): LiveData<Int>

    @Query("select count(*) from word where status=1 and category_id=:categoryId")
    fun countLearnedWord(categoryId: Int): Int

    @Query("select * from word where status=1")
    fun getLearnedWordList(): LiveData<List<Word>>

    @Query("select * from word where status=1 and category_id=:categoryId")
    fun getLearnedWordListByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select id from word where status=1 and category_id=:categoryId")
    fun getLearnedWordIdListByCategory(categoryId: Int): LiveData<List<Int>>

    @Query("select count(*) from word where status=1 and date>=:startTime and date<:endTime")
    fun countLearnedWordListInTime(startTime: Long, endTime: Long): LiveData<Int>

    @Query("select * from word where status=1 and date>=:startTime and date<:endTime")
    fun getLearnedWordListInTime(startTime: Long, endTime: Long): LiveData<List<Word>>

    // unknown word --------------
    @Query("select count(*) from word where status=0 and category_id=:categoryId")
    fun countUnknownWord(categoryId: Int): Int

    @Query("select * from word where status=0 and category_id=:categoryId")
    fun getUnknownWordListByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select id from word where status=0 and category_id=:categoryId")
    fun getUnknownWordIdListByCategory(categoryId: Int): LiveData<List<Int>>

    @Query("select * from word where status=0")
    fun getUnknownWordList(): LiveData<List<Word>>

    @Query("select id from word where status=0")
    fun getUnknownWordIdList(): LiveData<List<Int>>

    //-------

    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Delete
    fun delete(word: Word)
}