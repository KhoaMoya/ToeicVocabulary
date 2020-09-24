package com.khoa.toeicvocabulary.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var spelling: String?,
    var mean: String,
    var status: Int,
    @ColumnInfo(name = "category_id")
    var categoryId: Int,
    var date : Long
)