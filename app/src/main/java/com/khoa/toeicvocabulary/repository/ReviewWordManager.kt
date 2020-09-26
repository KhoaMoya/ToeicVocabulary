package com.khoa.toeicvocabulary.repository

import com.khoa.toeicvocabulary.models.Word
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewWordManager @Inject constructor() {
    var title: String = ""
        private set
    private val wordList = ArrayList<Word>()

    fun putWords(list: List<Word>, title: String) {
        this.title = title
        wordList.clear()
        wordList.addAll(list)
    }

    fun getWordList() = wordList

    fun clear() {
        wordList.clear()
    }

    fun isEmpty() = wordList.isEmpty()
}