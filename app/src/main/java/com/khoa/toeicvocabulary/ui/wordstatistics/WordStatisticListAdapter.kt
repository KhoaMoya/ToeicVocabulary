package com.khoa.toeicvocabulary.ui.wordstatistics

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khoa.toeicvocabulary.bases.BaseWordRcvAdapter
import com.khoa.toeicvocabulary.bases.ExpandableWord
import com.khoa.toeicvocabulary.databinding.WordItemBinding

class WordStatisticListAdapter : BaseWordRcvAdapter(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWordViewHolder {
        if(layoutInflater==null) layoutInflater = LayoutInflater.from(parent.context)
        return WordStatisticViewHolder(WordItemBinding.inflate(layoutInflater!!, parent, false))
    }

    override fun onBindViewHolder(holder: BaseWordViewHolder, position: Int) {
        holder.bind(wordsList?.get(position))
    }

    inner class WordStatisticViewHolder(binding: WordItemBinding) : BaseWordViewHolder(binding){

        init {
            binding.imgSpeech.setOnClickListener{
                itemSpellClickListener?.onClickItem(binding.expandableWord!!.word)
            }
        }

        override fun bind(expandableWord: ExpandableWord?) {
            (binding as WordItemBinding).apply {
                this.expandableWord = expandableWord
                executePendingBindings()
            }
        }

    }
}