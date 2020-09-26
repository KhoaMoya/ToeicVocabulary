package com.khoa.toeicvocabulary.ui.review

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.setTextMultiline
import kotlinx.android.synthetic.main.activity_review.*
import javax.inject.Inject

class ReviewActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var mViewModel: ReviewViewModel
    var isSeleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_review)

        txtTitleReview.text = mViewModel.reviewWordManager.title
        initActions()
        subscribeUi()
        startPlay()
    }

    private fun initActions() {
        imgBack.setOnClickListener { onBackPressed() }
        wordLayout.setOnClickListener {
            (application as MyApplication).speak(mViewModel.currentWord.value?.word?.name)
        }
        btnNextQuestion.setOnClickListener {
            hideBtnNextQuestion()
            mViewModel.nextQuestion()
        }

        answer0.setOnClickListener(this)
        answer1.setOnClickListener(this)
        answer2.setOnClickListener(this)
        answer3.setOnClickListener(this)
    }

    private fun startPlay() {
        mViewModel.startPlay()
    }

    private fun subscribeUi() {
        mViewModel.currentWord.observe(this, {
            txtWord.text = it.word.name
            txtSpelling.text = it.word.spelling
            setTextMultiline(answer0, it.answers[0])
            setTextMultiline(answer1, it.answers[1])
            setTextMultiline(answer2, it.answers[2])
            setTextMultiline(answer3, it.answers[3])
        })
        mViewModel.correctCount.observe(this, {
            txtCorrectCount.text = it.toString()
        })
        mViewModel.wordIndex.observe(this, {
            txtProgress.text =
                it.toString() + "/" + mViewModel.reviewWordManager.getWordList().size.toString()
        })

        mViewModel.answerState.observe(this, {
            showResult(it)
        })
    }

    private fun showResult(answerState: AnswerState) {
        when (answerState) {
            AnswerState.CORRECT -> showResultBackground()
            AnswerState.INCORRECT -> {
                showResultBackground()
                showBtnNextQuestion()
            }
            AnswerState.NEXT -> {
                isSeleted = false
                clearSelectedBackground()
            }
            AnswerState.FINISHED -> showFinishDialog()
        }
    }

    private fun showFinishDialog() {
        FinishReviewDialogFragment(mViewModel).show(supportFragmentManager, "")
    }

    private fun showResultBackground() {
        when (mViewModel.currentWord.value?.correctIndex) {
            0 -> answer0.setBackgroundResource(R.drawable.bg_result_answer)
            1 -> answer1.setBackgroundResource(R.drawable.bg_result_answer)
            2 -> answer2.setBackgroundResource(R.drawable.bg_result_answer)
            3 -> answer3.setBackgroundResource(R.drawable.bg_result_answer)
        }
    }

    override fun onClick(v: View?) {
        if (!isSeleted) {
            isSeleted = true
            setSelectedBackground(v)
            var selectedIndex = -1
            when (v?.id) {
                R.id.answer0 -> selectedIndex = 0
                R.id.answer1 -> selectedIndex = 1
                R.id.answer2 -> selectedIndex = 2
                R.id.answer3 -> selectedIndex = 3
            }
            if (selectedIndex >= 0) mViewModel.selectAnswer(selectedIndex)
        }
    }

    private fun showBtnNextQuestion() {
        btnNextQuestion.visibility = View.VISIBLE
        btnNextQuestion.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up_in))
    }

    private fun hideBtnNextQuestion() {
        btnNextQuestion.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down_out))
        btnNextQuestion.visibility = View.GONE
    }

    private fun setSelectedBackground(view: View?) {
        clearSelectedBackground()
        view?.setBackgroundResource(R.drawable.bg_selected_answer)
    }

    private fun clearSelectedBackground() {
        answer0.setBackgroundResource(R.drawable.bg_unselected_answer)
        answer1.setBackgroundResource(R.drawable.bg_unselected_answer)
        answer2.setBackgroundResource(R.drawable.bg_unselected_answer)
        answer3.setBackgroundResource(R.drawable.bg_unselected_answer)
    }

    private fun showConfirmQuitDialog() {
        AlertDialog.Builder(this)
            .setMessage("Quit review ?")
            .setPositiveButton("Quit") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onBackPressed() {
        if (mViewModel.answerState.value == AnswerState.FINISHED) {
            super.onBackPressed()
        } else {
            showConfirmQuitDialog()
        }
    }
}