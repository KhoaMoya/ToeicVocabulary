package com.khoa.toeicvocabulary.models

import androidx.recyclerview.widget.DiffUtil

class ExpandableWord (var isExpanded: Boolean, var word: Word){

    class DiffCallback : DiffUtil.ItemCallback<ExpandableWord>() {

        override fun areItemsTheSame(oldItem: ExpandableWord, newItem: ExpandableWord): Boolean {
            return oldItem.word.id == newItem.word.id
        }

        override fun areContentsTheSame(oldItem: ExpandableWord, newItem: ExpandableWord): Boolean {
            return oldItem.word.id == newItem.word.id
                    && oldItem.word.status == newItem.word.status
        }
    }
}