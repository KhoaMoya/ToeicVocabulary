package com.khoa.toeicvocabulary.ui.detailcategory.page

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.databinding.FragmentLearnWordBinding
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryViewModel
import com.khoa.toeicvocabulary.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LearnWordFragment(val pageType: PageType) : Fragment(), LearnWordChangeListener {

    @Inject
    lateinit var mViewModel: DetailCategoryViewModel
    lateinit var mBinding: FragmentLearnWordBinding
    val wordAdapter = LearnWordRcvAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).detailCategoryCompenent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLearnWordBinding.inflate(inflater).also {
            mBinding = it
            wordAdapter.learnListener = this
            mBinding.rcvCategory.adapter = wordAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriberUi()
    }

    private fun subscriberUi() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(250)
            when (pageType) {
                PageType.ALL -> {
                    mViewModel.multableAllWordList.observe(viewLifecycleOwner, {
                        wordAdapter.setWordList(it)
                    })
                }
                PageType.Known -> {
                    mViewModel.multableKnownWordList.observe(viewLifecycleOwner, {
                        wordAdapter.setWordList(it)
                    })
                }
                PageType.UnKnown -> {
                    mViewModel.multableUnknownWordList.observe(viewLifecycleOwner, {
                        wordAdapter.setWordList(it)
                    })
                }
            }

        }
    }

    override fun updateWord(word: Word) {
        mViewModel.updateWord(word)
    }
}