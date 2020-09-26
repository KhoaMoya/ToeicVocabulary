package com.khoa.toeicvocabulary.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.repository.AppRepository
import com.khoa.toeicvocabulary.repository.ReviewWordManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class ReviewViewModel @Inject constructor(
    val repository: AppRepository,
    val reviewWordManager: ReviewWordManager
) : ViewModel() {

    val correctCount = MutableLiveData(0)
    val wordIndex = MutableLiveData(0)
    var mWordList = reviewWordManager.getWordList()
    lateinit var mReviewWordList: List<ReviewWord>
    val currentWord = MutableLiveData<ReviewWord>()
    val answerState = MutableLiveData(AnswerState.NEXT)

    fun startPlay() {
        answerState.value = AnswerState.NEXT
        viewModelScope.launch(Dispatchers.IO) {
            correctCount.postValue(0)
            wordIndex.postValue(1)
            mReviewWordList = mWordList.apply { shuffle() }.map { ReviewWord(it) }
            populateReviewWord(mReviewWordList[0])
            currentWord.postValue(mReviewWordList[0])
        }
    }

    suspend fun populateReviewWord(reviewWord: ReviewWord) {
        reviewWord.apply {
            selectedAnswerIndex = -1
            correctIndex = Random.nextInt(4)
            var randomMean: String
            answers.forEachIndexed { index, _ ->
                if (index == correctIndex) {
                    randomMean = word.mean
                } else {
                    do {
                        randomMean = repository.wordDao.getRandomWordMean()
                    } while (randomMean == word.mean)
                }
                answers[index] = randomMean
            }
        }
    }

    fun selectAnswer(selectedIndex: Int) {
        currentWord.value?.let {
            if (it.selectedAnswerIndex != -1) return@let

            it.selectedAnswerIndex = selectedIndex
            if (it.correctIndex == selectedIndex) {
                answerState.value = AnswerState.CORRECT
                val count = correctCount.value!! + 1
                correctCount.value = count
                nextQuestion()
            } else {
                answerState.value = AnswerState.INCORRECT
            }
        }
    }

    fun nextQuestion() {
        if (!isFinish()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val index = wordIndex.value!! + 1
                    wordIndex.postValue(index)
                    populateReviewWord(mReviewWordList[index-1])
                    currentWord.postValue(mReviewWordList[index-1])
                    answerState.postValue(AnswerState.NEXT)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        } else {
            answerState.postValue(AnswerState.FINISHED)
        }
    }

    private fun isFinish(): Boolean {
        return wordIndex.value!! >= mReviewWordList.size
    }
}

class ReviewWord(
    val word: Word,
    val answers: Array<String> = Array(4) { "" },
    var selectedAnswerIndex: Int = -1,
    var correctIndex: Int = -1
)
