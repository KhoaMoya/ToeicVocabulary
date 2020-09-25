package com.khoa.toeicvocabulary.ui.listcategory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.bases.CategoryRcvAdapter
import com.khoa.toeicvocabulary.bases.ItemClickListener
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryActivity
import com.khoa.toeicvocabulary.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_list_category.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListCategoryFragment : Fragment(), ItemClickListener<Category> {

    @Inject
    lateinit var mViewModel: ListCategoryViewModel
    val categoryAdapter = CategoryRcvAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryAdapter.itemClickListener = this
        rcvListCategory.adapter = categoryAdapter
        rcvListCategory.setHasFixedSize(true)
        setupActions()
        subscribeUi()
    }

    private fun setupActions(){
        imgBack.setOnClickListener{activity!!.onBackPressed()}
    }

    private fun subscribeUi(){
        GlobalScope.launch (Dispatchers.Main){
            delay(300)
            mViewModel.allCategoryList.await().observe(viewLifecycleOwner, {
                txtTotalCategories.text = "Total: ${it.size}"
                categoryAdapter.setCategoriesList(it)
            })
        }
    }

    override fun onClickItem(item: Category) {
        val intent =  Intent(activity, DetailCategoryActivity::class.java)
        intent.putExtra(HomeFragment.CATEGORY_KEY, item)
        startActivity(intent)
    }

}