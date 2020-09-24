package com.khoa.toeicvocabulary.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var mean: String,
    var known: Int,
    @ColumnInfo(name = "word_count")
    var wordCount: Int,
    @ColumnInfo(name = "update_time")
    var updateTime: Long
)