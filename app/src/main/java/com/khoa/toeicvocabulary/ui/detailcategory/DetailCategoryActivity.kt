package com.khoa.toeicvocabulary.ui.detailcategory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.databinding.ActivityDetailCategoryBinding
import com.khoa.toeicvocabulary.di.component.DetailCategoryComponent
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.home.HomeFragment
import com.khoa.toeicvocabulary.ui.review.ReviewActivity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DetailCategoryActivity : AppCompatActivity() {
    @Inject
    lateinit var mViewModel: DetailCategoryViewModel
    lateinit var mBinding: ActivityDetailCategoryBinding
    lateinit var detailCategoryComponent: DetailCategoryComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getSerializableExtra(HomeFragment.CATEGORY_KEY) as Category?
        detailCategoryComponent = (application as MyApplication).appComponent.detailCategoryComponent()
            .category(category).build()
        detailCategoryComponent.inject(this)
        mViewModel.vpAdapter = WordsViewPagerAdapter(supportFragmentManager, category)

        ActivityDetailCategoryBinding.inflate(LayoutInflater.from(this)).also { binding ->
            binding.viewModel = mViewModel
            binding.vpCategory.adapter = mViewModel.vpAdapter
            binding.tablayout.setupWithViewPager(binding.vpCategory)
            mBinding = binding
        }
        setContentView(mBinding.root)
        initActions()
        subscriberUi()
    }

    private fun initActions() {
        mBinding.imgBack.setOnClickListener { onBackPressed() }
        mBinding.btnReview.setOnClickListener{
            startActivity(Intent(this, ReviewActivity::class.java))
        }
    }

    private fun subscriberUi(){
        val activity = this
        runBlocking {
            mViewModel.allWordList.await().observe(activity, {
                mBinding.tablayout.setBadgeText(0, it.size.toString())
                mViewModel.mutableAllWordList.value = it
                mViewModel.putWordListToGraph(it)
            })
            mViewModel.knownWordList.await().observe(activity, {
                mBinding.tablayout.setBadgeText(1, it.size.toString())
                mViewModel.mutableKnownWordList.value = it
            })
            mViewModel.unknownWordList.await().observe(activity, {
                mBinding.tablayout.setBadgeText(2, it.size.toString())
                mViewModel.mutableUnknownWordList.value = it
            })
        }
    }

}