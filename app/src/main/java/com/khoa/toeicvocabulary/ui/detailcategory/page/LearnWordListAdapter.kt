package com.khoa.toeicvocabulary.ui.detailcategory.page

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khoa.toeicvocabulary.bases.BaseWordRcvAdapter
import com.khoa.toeicvocabulary.bases.ExpandableWord
import com.khoa.toeicvocabulary.databinding.LearnWordItemBinding

class LearnWordListAdapter : BaseWordRcvAdapter() {

    var learnListener: LearnWordChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnWordViewHolder {
        if(layoutInflater==null) layoutInflater = LayoutInflater.from(parent.context)
        return LearnWordViewHolder(LearnWordItemBinding.inflate(layoutInflater!!, parent, false))
    }

    override fun onBindViewHolder(holder: BaseWordViewHolder, position: Int) {
        holder.bind(wordsList?.get(position))
    }

    inner class LearnWordViewHolder(binding: LearnWordItemBinding) : BaseWordViewHolder(binding){

        init {
            binding.cbKnown.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (!compoundButton.isPressed) return@setOnCheckedChangeListener
                binding.expandableWord?.let {
                    it.word.status = if (isChecked) 1 else 0
                    it.word.date = System.currentTimeMillis()
                    learnListener?.updateWord(it.word)
                }
            }
            binding.imgSpeech.setOnClickListener{
                itemSpellClickListener?.onClickItem(binding.expandableWord!!.word)
            }
        }

        override fun bind(expandableWord: ExpandableWord?) {
            (binding as LearnWordItemBinding).apply {
                this.expandableWord = expandableWord
                executePendingBindings()
            }
        }

    }

}