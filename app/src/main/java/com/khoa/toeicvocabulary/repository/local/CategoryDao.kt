package com.khoa.toeicvocabulary.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.khoa.toeicvocabulary.models.Category

@Dao
interface CategoryDao {
    @Query("select * from category order by name asc")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("select * from category order by update_time desc limit 6")
    fun getCategoriesRecently(): LiveData<List<Category>>

    @Query("update category set known=(select count(*) from word where word.category_id = category.id and word.status = 1), update_time=:time where category.id =:id")
    fun updateKnownWords(id: Int, time: Long)

    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)
}