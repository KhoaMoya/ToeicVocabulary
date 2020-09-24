package com.khoa.toeicvocabulary.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.models.Word

@Database(entities = [Category::class, Word::class], exportSchema = false, version = 1)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun wordDao(): WordDao
}