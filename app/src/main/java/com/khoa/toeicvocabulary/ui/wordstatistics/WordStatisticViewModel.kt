package com.khoa.toeicvocabulary.ui.wordstatistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.bases.StatisticType
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.AppRepository
import com.khoa.toeicvocabulary.repository.ReviewWordManager
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

class WordStatisticViewModel @Inject constructor(
    val repository: AppRepository,
    val reviewWordManager: ReviewWordManager
) : ViewModel() {
    lateinit var wordList: Deferred<LiveData<List<Word>>>
    val titleName = MutableLiveData<String>()

    fun initWordList(statisticType: StatisticType) {
        when (statisticType) {
            StatisticType.TODAY -> {
                titleName.value = "Words learn today"
                wordList = viewModelScope.async { repository.getWordLearnedToday() }
            }
            StatisticType.WEEK -> {
                titleName.value = "Words learn this week"
                wordList = viewModelScope.async { repository.getWordLearnedThisWeek() }
            }
            StatisticType.MONTH -> {
                titleName.value = "Words learn this month"
                wordList = viewModelScope.async { repository.getWordLearnedThisMonth() }
            }
        }
    }

    fun putWordListToGraph(list: List<Word>) {
        reviewWordManager.putWords(list, "Review ${titleName.value}")
    }
}