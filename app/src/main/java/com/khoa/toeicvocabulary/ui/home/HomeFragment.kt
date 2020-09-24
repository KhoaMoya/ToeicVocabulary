package com.khoa.toeicvocabulary.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.bases.ItemClickListener
import com.khoa.toeicvocabulary.databinding.FragmentHomeBinding
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryFragment
import com.khoa.toeicvocabulary.ui.main.MainActivity
import javax.inject.Inject

class HomeFragment : Fragment(), OnClickListener, ItemClickListener<Category> {

    @Inject
    lateinit var mViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(layoutInflater).also {
            mBinding = it
            mViewModel.categoryRcvAdapter.itemClickListener = this
            mBinding.viewmodel = mViewModel
            mBinding.rcvCategory.adapter = mViewModel.categoryRcvAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        subscribeUi()
    }

    private fun initActions() {
        mBinding.txtSeeAllWords.setOnClickListener(this)
        mBinding.txtSeeAllCategories.setOnClickListener(this)
        mBinding.imgEditTarget.setOnClickListener(this)
    }

    private fun subscribeUi() {
        with(mViewModel) {
            categoriesRecently.observe(viewLifecycleOwner, {
                mViewModel.categoryRcvAdapter.setCategoriesList(it)
            })
            countWordsLearnedToday.observe(viewLifecycleOwner, {
                mBinding.txtWordTodayValue.text = it.toString()
            })
            countWordsLearnedThisWeek.observe(viewLifecycleOwner, {
                mBinding.txtWordWeekValue.text = it.toString()
            })
            countWordsLearnedThisMonth.observe(viewLifecycleOwner, {
                mBinding.txtWordMonthValue.text = it.toString()
            })
            countAllWordsLearned.observe(viewLifecycleOwner, {
                mBinding.txtRatioAllWordsLearned.text = "$it/${mViewModel.countAllWords.value}"
            })
            countAllWords.observe(viewLifecycleOwner, {
                mBinding.txtRatioAllWordsLearned.text =
                    "${mViewModel.countAllWordsLearned.value}/$it"
            })
            isLoading.observe(viewLifecycleOwner, { isVisible ->
                if (isVisible) mBinding.progressLoading.visibility = View.VISIBLE
                else mBinding.progressLoading.visibility = View.GONE
            })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtSeeAllWords -> {
                (activity as MainActivity).dispatcherTo(DetailCategoryFragment())
            }
            else -> {
            }
        }
    }


    override fun onClickItem(item: Category) {
        (activity as MainActivity).dispatcherTo(DetailCategoryFragment(item))
    }
}