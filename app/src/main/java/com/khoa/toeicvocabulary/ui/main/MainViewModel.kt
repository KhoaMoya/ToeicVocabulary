package com.khoa.toeicvocabulary.ui.main

import androidx.lifecycle.ViewModel
import com.khoa.toeicvocabulary.repository.AppRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
}