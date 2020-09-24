package com.khoa.toeicvocabulary.bases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.toeicvocabulary.databinding.CategoryItemBinding
import com.khoa.toeicvocabulary.models.Category

class CategoryRcvAdapter :
    ListAdapter<Category, CategoryRcvAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    private var mCategoriesList: List<Category>? = null
    var itemClickListener: ItemClickListener<Category>? = null

    fun setCategoriesList(categories: List<Category>) {
        this.mCategoriesList = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context))
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        mCategoriesList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return mCategoriesList?.size ?: 0
    }

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onClickItem(binding.category!!)
            }
        }

        fun bind(category: Category) {
            binding.apply {
                this.category = category
                executePendingBindings()
            }
        }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
                && oldItem.known == newItem.known
                && oldItem.wordCount == newItem.wordCount
    }
}