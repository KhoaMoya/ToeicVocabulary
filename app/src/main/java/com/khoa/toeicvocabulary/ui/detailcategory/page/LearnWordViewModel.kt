package com.khoa.toeicvocabulary.ui.detailcategory.page

import androidx.lifecycle.ViewModel
import com.khoa.toeicvocabulary.repository.AppRepository
import javax.inject.Inject

class LearnWordViewModel @Inject constructor(val repository: AppRepository) : ViewModel() {
    val adapter = LearnWordRcvAdapter()




}