package com.khoa.toeicvocabulary.ui.detailcategory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.databinding.FragmentDetailCategoryBinding
import com.khoa.toeicvocabulary.di.component.DetailCategoryComponent
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.main.MainActivity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DetailCategoryFragment(val category: Category? = null) : Fragment() {

    @Inject
    lateinit var mViewModel: DetailCategoryViewModel
    lateinit var mBinding: FragmentDetailCategoryBinding
    lateinit var detailCategoryComponent: DetailCategoryComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailCategoryComponent = (activity!!.application as MyApplication).appComponent.detailCategoryComponent()
            .category(category).build()
        detailCategoryComponent.inject(this)
        (activity as MainActivity).detailCategoryCompenent = detailCategoryComponent
        mViewModel.vpAdapter = WordsViewPagerAdapter(childFragmentManager, category)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailCategoryBinding.inflate(inflater).also { binding ->
            binding.viewModel = mViewModel
            binding.vpCategory.adapter = mViewModel.vpAdapter
            binding.tablayout.setupWithViewPager(binding.vpCategory)
            mBinding = binding
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        subscriberUi()
    }

    private fun initActions() {
        mBinding.imgBack.setOnClickListener { activity?.onBackPressed() }
    }

    private fun subscriberUi(){
        runBlocking {
            mViewModel.allWordList.await().observe(viewLifecycleOwner, {
                mBinding.tablayout.setBadgeText(0, it.size.toString())
                mViewModel.multableAllWordList.value = it
            })
            mViewModel.knownWordList.await().observe(viewLifecycleOwner, {
                mBinding.tablayout.setBadgeText(1, it.size.toString())
                mViewModel.multableKnownWordList.value = it
            })
            mViewModel.unknownWordList.await().observe(viewLifecycleOwner, {
                mBinding.tablayout.setBadgeText(2, it.size.toString())
                mViewModel.multableUnknownWordList.value = it
            })
        }
    }
}