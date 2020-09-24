package com.khoa.toeicvocabulary.ui.detailcategory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.detailcategory.page.LearnWordFragment
import com.khoa.toeicvocabulary.ui.detailcategory.page.PageType

class WordsViewPagerAdapter(fragmentManager: FragmentManager, val category: Category?): FragmentPagerAdapter(fragmentManager) {

    private val mAllListFragment = LearnWordFragment(PageType.ALL)
    private val mKnownFragment = LearnWordFragment(PageType.Known)
    private val mUnknownFragment = LearnWordFragment(PageType.UnKnown)

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