package com.khoa.toeicvocabulary.ui.detailcategory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.ui.detailcategory.page.LearnWordFragment

class WordsViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val mAllListFragment = LearnWordFragment()
    private val mKnownFragment = LearnWordFragment()
    private val mUnknownFragment = LearnWordFragment()

    fun updateAllList(words: List<Word>){
        mAllListFragment.setWordsList(words)
    }

    fun updateKnownList(words: List<Word>){
        mKnownFragment.setWordsList(words)
    }

    fun updateUnknownList(words: List<Word>){
        mUnknownFragment.setWordsList(words)
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return mAllListFragment
            }
            1 -> {
                return mKnownFragment
            }
            2 -> {
                return mUnknownFragment
            }
            else -> {
                return mAllListFragment
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "All"
            }
            1 -> {
                return "Known"
            }
            2 -> {
                return "Unknown"
            }
            else -> {
                return ""
            }
        }
    }
}