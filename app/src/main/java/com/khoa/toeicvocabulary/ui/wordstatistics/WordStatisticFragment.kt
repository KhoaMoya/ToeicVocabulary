package com.khoa.toeicvocabulary.ui.wordstatistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.bases.ItemClickListener
import com.khoa.toeicvocabulary.models.Word
import kotlinx.android.synthetic.main.fragment_word_statistic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordStatisticFragment(val statisticType: StatisticType) : Fragment(), ItemClickListener<Word>{
    @Inject
    lateinit var mViewModel: WordStatisticViewModel
    private val wordAdapter = WordStatisticListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
        mViewModel.initWordList(statisticType)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        setTitle()
        wordAdapter.itemSpellClickListener = this
        rcvListWord.adapter = wordAdapter
        subscribeUi()
    }

    private fun setTitle(){
        when (statisticType) {
            StatisticType.TODAY -> {
                txtTitleWordStatistic.text = "Words learn today"
            }
            StatisticType.WEEK -> {
                txtTitleWordStatistic.text = "Words learn this week"
            }
            StatisticType.MONTH -> {
                txtTitleWordStatistic.text = "Words learn this month"
            }
        }
    }

    private fun initActions(){
        imgBack.setOnClickListener { activity!!.onBackPressed() }
    }

    private fun subscribeUi(){

        GlobalScope.launch(Dispatchers.Main) {
            delay(300)
            mViewModel.wordList.await().observe(viewLifecycleOwner, {
                txtTotalWords.text = "Total: ${it.size}"
                wordAdapter.setWordList(it)
            })
        }
    }

    override fun onClickItem(item: Word) {
    }
}