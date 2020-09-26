package com.khoa.toeicvocabulary.ui.review

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.khoa.toeicvocabulary.R
import kotlinx.android.synthetic.main.dialog_fragment_finish_review.*

class FinishReviewDialogFragment(private val reviewViewModel: ReviewViewModel) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return inflater.inflate(R.layout.dialog_fragment_finish_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        showResult()
        animationShow()
    }

    private fun showResult() {
        txtCorrectCount.text = reviewViewModel.correctCount.value.toString()
        txtTotal.text = reviewViewModel.reviewWordManager.getWordList().size.toString()
    }

    private fun initActions() {
        btnCloseAndBack.setOnClickListener {
            animationHide(false)
        }
        btnRetry.setOnClickListener {
            animationHide(true)
        }
    }

    private fun animationShow() {
        resultLayout.visibility = View.VISIBLE
        resultLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up_in))

        Handler().postDelayed({
            btnRetry.visibility = View.VISIBLE
            btnRetry.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up_in))
        }, 200)

        Handler().postDelayed({
            btnCloseAndBack.visibility = View.VISIBLE
            btnCloseAndBack.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.slide_up_in
                )
            )
        }, 400)
    }

    private fun animationHide(retry: Boolean) {
        btnCloseAndBack.startAnimation(
            AnimationUtils.loadAnimation(
                activity,
                R.anim.slide_down_out
            )
        )
        btnCloseAndBack.visibility = View.INVISIBLE


        Handler().postDelayed({
            btnRetry.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_down_out))
            btnRetry.visibility = View.INVISIBLE
        }, 200)


        Handler().postDelayed({
            resultLayout.startAnimation(
                AnimationUtils.loadAnimation(
                    activity,
                    R.anim.slide_down_out
                )
            )
            resultLayout.visibility = View.INVISIBLE
        }, 400)

        Handler().postDelayed({
            dialog?.dismiss()
            if (retry) {
                reviewViewModel.startPlay()
            } else {
                activity?.finish()
            }
        }, 800)
    }
}