package com.khoa.toeicvocabulary.ui.detailcategory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.databinding.FragmentDetailCategoryBinding
import com.khoa.toeicvocabulary.di.component.DaggerAppComponent
import com.khoa.toeicvocabulary.models.Category
import javax.inject.Inject

class DetailCategoryFragment(val category: Category? = null) : Fragment() {

    @Inject
    lateinit var mViewModel: DetailCategoryViewModel
    lateinit var mBinding: FragmentDetailCategoryBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder()
            .application(activity!!.application)
            .category(category)
            .build()
            .inject(this)
        mViewModel.vpAdapter = WordsViewPagerAdapter(childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailCategoryBinding.inflate(inflater).also {
            it.viewModel = mViewModel
            it.vpCategory.offscreenPageLimit = 5
            it.vpCategory.adapter = mViewModel.vpAdapter
            it.tablayout.setupWithViewPager(it.vpCategory)
            mBinding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        subscriberUi()
//        mViewModel.loadData()
    }

    private fun initActions() {
        mBinding.imgBack.setOnClickListener { activity?.onBackPressed() }
    }

    private fun subscriberUi() {
        mViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                mBinding.progressLoading.visibility = View.VISIBLE
            } else {
                mBinding.progressLoading.visibility = View.GONE
            }
        })

        mViewModel.reponsitory.wordDao.getUnknownWordsByCategory(category!!.id).observe(viewLifecycleOwner, {
            mViewModel.vpAdapter.updateUnknownList(it)
            mBinding.tablayout.setBadgeText(2, it.size.toString())
        })

//        mViewModel.allWordsList.observe(viewLifecycleOwner, {
//            mViewModel.vpAdapter.updateAllList(it)
//            mBinding.tablayout.setBadgeText(0, it.size.toString())
//        })
//        mViewModel.knownWordsList.observe(viewLifecycleOwner, {
//            mViewModel.vpAdapter.updateKnownList(it)
//            mBinding.tablayout.setBadgeText(1, it.size.toString())
//        })
//        mViewModel.unknownWordsList.observe(viewLifecycleOwner, {
//            mViewModel.vpAdapter.updateUnknownList(it)
//            mBinding.tablayout.setBadgeText(2, it.size.toString())
//        })
    }
}