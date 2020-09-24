package com.khoa.toeicvocabulary.di.component

import com.khoa.toeicvocabulary.di.scope.DetailCategoryScope
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryFragment
import com.khoa.toeicvocabulary.ui.detailcategory.page.LearnWordFragment
import dagger.BindsInstance
import dagger.Subcomponent

@DetailCategoryScope
@Subcomponent
interface DetailCategoryComponent {

    @Subcomponent.Builder
    interface Builder{
        fun category(@BindsInstance category: Category?): Builder
        fun build(): DetailCategoryComponent
    }

    fun inject(detailCategoryFragment: DetailCategoryFragment)
    fun inject(leardWordFragment: LearnWordFragment)
}