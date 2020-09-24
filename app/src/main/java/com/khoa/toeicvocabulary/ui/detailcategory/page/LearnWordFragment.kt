package com.khoa.toeicvocabulary.ui.detailcategory.page

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.databinding.FragmentLearnWordBinding
import com.khoa.toeicvocabulary.models.Word
import javax.inject.Inject

class LearnWordFragment : Fragment() , LearnWordChangeListener{

    @Inject
    lateinit var mViewModel : LearnWordViewModel
    lateinit var mBinding : FragmentLearnWordBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLearnWordBinding.inflate(inflater).also {
            mBinding = it
            mViewModel.adapter.learnListener = this
            mBinding.rcvCategory.adapter = mViewModel.adapter
        }.root
    }

    fun setWordsList(words: List<Word>){
        mViewModel.adapter.setWordList(words)
    }

    override fun updateWord(word: Word) {
        mViewModel.updateWord(word)
    }
}