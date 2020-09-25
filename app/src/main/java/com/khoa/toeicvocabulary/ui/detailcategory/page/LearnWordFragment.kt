package com.khoa.toeicvocabulary.ui.detailcategory.page

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.bases.ItemClickListener
import com.khoa.toeicvocabulary.databinding.FragmentLearnWordBinding
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryActivity
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LearnWordFragment(val pageType: PageType) : Fragment(), LearnWordChangeListener, ItemClickListener<Word> {

    @Inject
    lateinit var mViewModel: DetailCategoryViewModel
    lateinit var mBinding: FragmentLearnWordBinding
    private val wordAdapter = LearnWordListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DetailCategoryActivity).detailCategoryComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLearnWordBinding.inflate(inflater).also {
            mBinding = it
            wordAdapter.learnListener = this
            wordAdapter.itemSpellClickListener = this
            mBinding.rcvCategory.adapter = wordAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriberUi()
    }

    private fun subscriberUi() {
        GlobalScope.launch(Dispatchers.Main) {
//            delay(250)
            when (pageType) {
                PageType.ALL -> {
                    mViewModel.mutableAllWordList.observe(viewLifecycleOwner, {
                        wordAdapter.setWordList(it)
                    })
                }
                PageType.Known -> {
                    mViewModel.mutableKnownWordList.observe(viewLifecycleOwner, {
                        wordAdapter.setWordList(it)
                    })
                }
                PageType.UnKnown -> {
                    mViewModel.mutableUnknownWordList.observe(viewLifecycleOwner, {
                        wordAdapter.setWordList(it)
                    })
                }
            }

        }
    }

    override fun updateWord(word: Word) {
        mViewModel.updateWord(word)
    }

    override fun onClickItem(item: Word) {

    }
}