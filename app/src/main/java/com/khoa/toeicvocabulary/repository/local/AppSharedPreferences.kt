package com.khoa.toeicvocabulary.repository.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.khoa.toeicvocabulary.PreferenceName
import com.khoa.toeicvocabulary.TargetWordsPerDay

class AppSharedPreferences (application: Application) {
    var pref: SharedPreferences = application.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)

    fun getTargetWordsDay(): Int {
        return pref.getInt(TargetWordsPerDay, 10)
    }

    fun setTargetWordsDay(target: Int){
        pref.edit().putInt(TargetWordsPerDay, target).apply()
    }
}