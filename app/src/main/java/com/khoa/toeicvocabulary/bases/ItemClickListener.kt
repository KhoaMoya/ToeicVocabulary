package com.khoa.toeicvocabulary.bases

interface ItemClickListener<in T> {
    fun onClickItem(item : T)
}