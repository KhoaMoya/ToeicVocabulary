package com.khoa.toeicvocabulary.ui.detailcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailCategoryViewModel @Inject constructor(
    val reponsitory: AppRepository,
    val category: Category?
) : ViewModel() {
    lateinit var vpAdapter: WordsViewPagerAdapter
    var allWordsList: LiveData<List<Word>> = MutableLiveData()
    var knownWordsList: LiveData<List<Word>> = MutableLiveData()
    var unknownWordsList: LiveData<List<Word>> = MutableLiveData()
    val isLoading = MutableLiveData(true)
    val titleName = MutableLiveData("")
    val delayLoadingTime = 500L

//    init {
//        viewModelScope.launch (Dispatchers.IO){
//            allWordsList = reponsitory.getAllWords(category?.id)
//            knownWordsList = reponsitory.getKnownWords(category?.id)
//            unknownWordsList = reponsitory.getUnKnownWords(category?.id)
//        }
//
//    }

    fun loadData() {
        if (category != null) {
            titleName.value = category.name
            isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    launch { allWordsList = reponsitory.wordDao.getAllWordsByCategory(category.id) }
                    launch {
                        knownWordsList = reponsitory.wordDao.getLearnedWordsByCategory(category.id)
                    }
                    launch {
                        unknownWordsList =
                            reponsitory.wordDao.getUnknownWordsByCategory(category.id)
                    }
                    delay(delayLoadingTime)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isLoading.postValue(false)
                }
            }
        } else {
            titleName.value = "All words"
            isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    launch { allWordsList = reponsitory.wordDao.getAllWords() }
                    launch { knownWordsList = reponsitory.wordDao.getLearnedWords() }
                    launch { unknownWordsList = reponsitory.wordDao.getUnknownWords() }
                    delay(delayLoadingTime)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isLoading.postValue(false)
                }
            }
        }
    }
}