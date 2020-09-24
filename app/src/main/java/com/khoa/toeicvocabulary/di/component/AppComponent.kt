package com.khoa.toeicvocabulary.di.component

import android.app.Application
import com.khoa.toeicvocabulary.di.modules.DbModule
import com.khoa.toeicvocabulary.di.modules.PreferencesModule
import com.khoa.toeicvocabulary.di.modules.RepositoryModule
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryFragment
import com.khoa.toeicvocabulary.ui.detailcategory.page.LearnWordFragment
import com.khoa.toeicvocabulary.ui.home.HomeFragment
import com.khoa.toeicvocabulary.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DbModule::class, RepositoryModule::class, PreferencesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance fun application( application: Application): Builder
        @BindsInstance fun category( category: Category?): Builder
    }

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(learnWordFragment: LearnWordFragment)
    fun inject(detailCategoryFragment: DetailCategoryFragment)
}