package com.khoa.toeicvocabulary.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.khoa.toeicvocabulary.models.Word

@Dao
interface WordDao {
    // random mean
    @Query("select mean from word order by random() limit 1")
    fun getRandomWordMean(): String

    // word --------------
    @Query("select * from word where id=:id")
    fun getWord(id: Int): Word

    // all word -----------
    @Query("select count(*) from word")
    fun countAllWords(): LiveData<Int>

    @Query("select * from word")
    fun getAllWords(): LiveData<List<Word>>

    @Query("select * from word where category_id=:categoryId")
    fun getAllWordsByCategory(categoryId: Int): LiveData<List<Word>>

    // known word ----------
    @Query("select count(*) from word where status=1")
    fun countLearnedWord(): LiveData<Int>

    @Query("select count(*) from word where status=1 and category_id=:categoryId")
    fun countLearnedWord(categoryId: Int): Int

    @Query("select * from word where status=1")
    fun getLearnedWordList(): LiveData<List<Word>>

    @Query("select * from word where status=1 and category_id=:categoryId")
    fun getLearnedWordListByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select count(*) from word where status=1 and date>=:startTime and date<:endTime")
    fun countLearnedWordListInTime(startTime: Long, endTime: Long): LiveData<Int>

    @Query("select * from word where status=1 and date>=:startTime and date<:endTime")
    fun getLearnedWordListInTime(startTime: Long, endTime: Long): LiveData<List<Word>>

    // unknown word --------------
    @Query("select count(*) from word where status=0 and category_id=:categoryId")
    fun countUnknownWord(categoryId: Int): Int

    @Query("select * from word where status=0 and category_id=:categoryId")
    fun getUnknownWordListByCategory(categoryId: Int): LiveData<List<Word>>

    @Query("select * from word where status=0")
    fun getUnknownWordList(): LiveData<List<Word>>

    //-------

    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Delete
    fun delete(word: Word)
}