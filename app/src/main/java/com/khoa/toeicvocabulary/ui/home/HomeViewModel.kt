package com.khoa.toeicvocabulary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.bases.CategoryRcvAdapter
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    val categoryRcvAdapter = CategoryRcvAdapter()
    val targetWordsPerDay = MutableLiveData(repository.preference.getTargetWordsDay())

    lateinit var categoriesRecently: LiveData<List<Category>>
    lateinit var countWordsLearnedToday: LiveData<Int>
    lateinit var countWordsLearnedThisWeek: LiveData<Int>
    lateinit var countWordsLearnedThisMonth: LiveData<Int>
    lateinit var countAllWordsLearned: LiveData<Int>
    lateinit var countAllWords: LiveData<Int>
    var isLoading = MutableLiveData(true)
    val job = Job()

    lateinit var countWordInDayRecently : ArrayList<DayWord>

    init {
        isLoading.value = true
        viewModelScope.launch(job + Dispatchers.IO) {
            try {
                launch { categoriesRecently = repository.categoryDao.getCategoriesRecently() }
                launch { countWordsLearnedToday = repository.countWordListLearnedToday() }
                launch { countWordsLearnedThisWeek = repository.countWordListLearnedThisWeek() }
                launch { countWordsLearnedThisMonth = repository.countWordLearnedThisMonth() }
                launch { countAllWordsLearned = repository.wordDao.countLearnedWord() }
                launch { countAllWords = repository.wordDao.countAllWords() }
                launch { countWordInDayRecently = repository.getDayWordRecentlyList() }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                withContext(Dispatchers.Main) { isLoading.value = false }
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun saveTargetWordPerDay(number: Int){
        repository.preference.setTargetWordsDay(number)
        targetWordsPerDay.value = number
    }
}

class DayWord(
    var dateName: String = "",
    var startTime: Long = 0L,
    var endTime: Long = 0L
){
    lateinit var count: LiveData<Int>
}