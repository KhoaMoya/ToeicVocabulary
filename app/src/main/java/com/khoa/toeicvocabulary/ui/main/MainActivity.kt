package com.khoa.toeicvocabulary.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.ui.home.HomeFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.mainPlaceHolder, HomeFragment())
            .commit()
    }

    fun dispatcherTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, // enter
                R.anim.fade_out, // exit
                R.anim.fade_in, // pop_enter
                R.anim.slide_out // pop_exit
            )
            .add(R.id.mainPlaceHolder, fragment)
            .addToBackStack(null)
            .commit()
    }
}