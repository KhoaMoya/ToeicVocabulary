package com.khoa.toeicvocabulary.di.modules

import com.khoa.toeicvocabulary.repository.AppRepository
import com.khoa.toeicvocabulary.repository.local.AppSharedPreferences
import com.khoa.toeicvocabulary.repository.local.CategoryDao
import com.khoa.toeicvocabulary.repository.local.WordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        categoryDao: CategoryDao,
        wordDao: WordDao,
        preferences: AppSharedPreferences
    ): AppRepository {
        return AppRepository(categoryDao, wordDao, preferences)
    }
}