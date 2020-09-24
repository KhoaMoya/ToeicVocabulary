package com.khoa.toeicvocabulary.di.modules

import android.app.Application
import com.khoa.toeicvocabulary.repository.local.AppSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule{
    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application):AppSharedPreferences{
        return AppSharedPreferences(application)
    }
}