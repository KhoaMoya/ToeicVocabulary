package com.khoa.toeicvocabulary.ui.wordstatistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.bases.ItemClickListener
import com.khoa.toeicvocabulary.bases.StatisticType
import com.khoa.toeicvocabulary.models.Word
import com.khoa.toeicvocabulary.ui.review.ReviewActivity
import kotlinx.android.synthetic.main.fragment_word_statistic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordStatisticFragment(val statisticType: StatisticType) : Fragment(),
    ItemClickListener<Word> {
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
        wordAdapter.itemSpellClickListener = this
        rcvListWord.adapter = wordAdapter
        subscribeUi()


    }

    private fun initActions() {
        imgBack.setOnClickListener { activity!!.onBackPressed() }
        btnReview.setOnClickListener {
            startActivity(Intent(activity!!, ReviewActivity::class.java))
        }
    }

    private fun subscribeUi() {

        GlobalScope.launch(Dispatchers.Main) {
            delay(300)
            mViewModel.wordList.await().observe(viewLifecycleOwner, {
                txtTotalWords.text = "Total: ${it.size}"
                wordAdapter.setWordList(it)
                mViewModel.putWordListToGraph(it)
            })
        }

        mViewModel.titleName.observe(viewLifecycleOwner, { txtTitleWordStatistic.text = it })
    }

    override fun onClickItem(item: Word) {
        (activity?.application as MyApplication).speak(item.name)
    }
}