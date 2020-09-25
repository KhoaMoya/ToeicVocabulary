package com.khoa.toeicvocabulary.di.component

import android.app.Application
import com.khoa.toeicvocabulary.di.modules.DbModule
import com.khoa.toeicvocabulary.di.modules.PreferencesModule
import com.khoa.toeicvocabulary.di.modules.RepositoryModule
import com.khoa.toeicvocabulary.repository.AppRepository
import com.khoa.toeicvocabulary.ui.home.HomeFragment
import com.khoa.toeicvocabulary.ui.listcategory.ListCategoryFragment
import com.khoa.toeicvocabulary.ui.main.MainActivity
import com.khoa.toeicvocabulary.ui.wordstatistics.WordStatisticFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DbModule::class,
        RepositoryModule::class,
        PreferencesModule::class,
        AppSubcomponents::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun getRepository(): AppRepository

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(listCategoryFragment: ListCategoryFragment)
    fun inject(wordStatisticFragment: WordStatisticFragment)

    fun detailCategoryComponent(): DetailCategoryComponent.Builder
}