package com.khoa.toeicvocabulary.ui.detailcategory.page

import com.khoa.toeicvocabulary.models.Word

interface LearnWordChangeListener {
    fun updateWord(word: Word)
}