package com.khoa.toeicvocabulary

import android.app.Application
import com.khoa.toeicvocabulary.di.component.AppComponent
import com.khoa.toeicvocabulary.di.component.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        initComponent()
    }

    private fun initComponent(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }
}