package com.khoa.toeicvocabulary.bases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khoa.toeicvocabulary.models.ExpandableWord
import com.khoa.toeicvocabulary.models.Word

abstract class BaseWordRcvAdapter :
    ListAdapter<ExpandableWord, BaseWordRcvAdapter.BaseWordViewHolder>(ExpandableWord.DiffCallback()) {

    protected var wordsList: ArrayList<ExpandableWord>? = null
    protected var layoutInflater: LayoutInflater? = null
    var itemSpellClickListener: ItemClickListener<Word>? = null

    fun setWordList(words: List<Word>) {
        wordsList = ArrayList(words.map { ExpandableWord(false, it) })
        submitList(wordsList)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWordViewHolder

    abstract override fun onBindViewHolder(holder: BaseWordViewHolder, position: Int)

    override fun getItemCount(): Int {
        return wordsList?.size ?: 0
    }

    abstract inner class BaseWordViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                wordsList?.let {
                    it[adapterPosition].isExpanded = !it[adapterPosition].isExpanded
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        abstract fun bind(expandableWord: ExpandableWord?)
    }
}