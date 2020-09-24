package com.khoa.toeicvocabulary.ui.detailcategory.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.toeicvocabulary.databinding.LearnWordItemBinding
import com.khoa.toeicvocabulary.models.ExpandableWord
import com.khoa.toeicvocabulary.models.Word

// RecyclerView.Adapter<LearnWordRcvAdapter.LearnWordViewHolder>()
class LearnWordRcvAdapter :
    ListAdapter<ExpandableWord, LearnWordRcvAdapter.LearnWordViewHolder>(ExpandableWordDiffCallback()) {

    private var mWordsList: ArrayList<ExpandableWord>? = null
    var learnListener: LearnWordChangeListener? = null

    fun setWordList(words: List<Word>) {
        mWordsList = ArrayList(words.map { ExpandableWord(false, it) })
        submitList(mWordsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnWordViewHolder {
        return LearnWordViewHolder(LearnWordItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LearnWordViewHolder, position: Int) {
        holder.bind(mWordsList?.get(position))
    }

    override fun getItemCount(): Int {
        return mWordsList?.size ?: 0
    }

    inner class LearnWordViewHolder(val binding: LearnWordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                mWordsList?.let {
                    it[adapterPosition].isExpanded = !it[adapterPosition].isExpanded
                    notifyItemChanged(adapterPosition)
                }
            }
            binding.cbKnown.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (!compoundButton.isPressed) return@setOnCheckedChangeListener
                binding.expandableWord?.let {
                    it.word.status = if (isChecked) 1 else 0
                    it.word.date = System.currentTimeMillis()
                    learnListener?.updateWord(it.word)
                }
            }
        }

        fun bind(expandableWord: ExpandableWord?) {
            binding.apply {
                this.expandableWord = expandableWord
                executePendingBindings()
            }
        }
    }
}

private class ExpandableWordDiffCallback : DiffUtil.ItemCallback<ExpandableWord>() {
    override fun areItemsTheSame(oldItem: ExpandableWord, newItem: ExpandableWord): Boolean {
        return oldItem.word.id == newItem.word.id
    }

    override fun areContentsTheSame(oldItem: ExpandableWord, newItem: ExpandableWord): Boolean {
        return oldItem.word.id == newItem.word.id
                && oldItem.word.status == newItem.word.status

    }

}