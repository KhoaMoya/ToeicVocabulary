package com.khoa.toeicvocabulary.di.modules

import android.app.Application
import androidx.room.Room
import com.khoa.toeicvocabulary.DatabaseName
import com.khoa.toeicvocabulary.DbAssetFile
import com.khoa.toeicvocabulary.repository.local.AppDatabase
import com.khoa.toeicvocabulary.repository.local.CategoryDao
import com.khoa.toeicvocabulary.repository.local.WordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DatabaseName
        )
            .createFromAsset(DbAssetFile)
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideWordDao(appDatabase: AppDatabase): WordDao {
        return appDatabase.wordDao()
    }

}