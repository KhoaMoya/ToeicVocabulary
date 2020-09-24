package com.khoa.toeicvocabulary.ui.detailcategory.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LearnWordViewModel @Inject constructor(val reponsitory: AppRepository) : ViewModel() {
    val adapter = LearnWordRcvAdapter()

    fun updateWord(word: Word){
        viewModelScope.launch (Dispatchers.IO) {
            reponsitory.wordDao.update(word)
            reponsitory.categoryDao.updateKnownWords(word.categoryId)
        }
    }
}