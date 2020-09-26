package com.khoa.toeicvocabulary.ui.listcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.repository.AppRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

class ListCategoryViewModel @Inject constructor(val repository: AppRepository) : ViewModel() {
    var allCategoryList: Deferred<LiveData<List<Category>>> =
        viewModelScope.async { repository.categoryDao.getAllCategories() }
}