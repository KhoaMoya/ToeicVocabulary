package com.khoa.toeicvocabulary.ui.detailcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.di.scope.DetailCategoryScope
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.AppRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@DetailCategoryScope
class DetailCategoryViewModel @Inject constructor(
    val repository: AppRepository,
    val category: Category?
) : ViewModel() {
    lateinit var vpAdapter: WordsViewPagerAdapter

    var allWordList: Deferred<LiveData<List<Word>>>
    var knownWordList: Deferred<LiveData<List<Word>>>
    var unknownWordList: Deferred<LiveData<List<Word>>>
    val titleName = MutableLiveData<String>()

    val multableAllWordList = MutableLiveData<List<Word>>()
    val multableKnownWordList = MutableLiveData<List<Word>>()
    val multableUnknownWordList = MutableLiveData<List<Word>>()

    init {
        titleName.value = category?.name ?: "All words"
        allWordList = viewModelScope.async { repository.getAllWordList(category?.id) }
        knownWordList = viewModelScope.async { repository.getKnownWordList(category?.id) }
        unknownWordList = viewModelScope.async { repository.getUnKnownWordList(category?.id) }
    }

    fun updateWord(word: Word){
        viewModelScope.launch (Dispatchers.IO) {
            repository.wordDao.update(word)
            repository.categoryDao.updateKnownWords(word.categoryId)
        }
    }
}